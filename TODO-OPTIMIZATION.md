# Bio 项目待优化项清单

> 审查日期：2026-07-03
> 审查范围：后端（bio）+ 前端（bio-web）

---

## 一、🔴 致命问题（必须立即修复）

### 1.1【后端】安全配置严重漏洞

- `application.yml` 中**硬编码了数据库密码、Redis 密码、MinIO 凭证、RabbitMQ 密码、JWT 密钥**等敏感信息，没有使用环境变量或配置中心
- JWT 密钥过短过弱，容易被暴力破解
- **缺少授权校验**：多个删除接口（如删除评论、删除传记）只做了认证（Authentication），没有做权限校验（Authorization），任何登录用户都能删除他人数据
- Actuator 和 Druid 监控端点暴露，没有做访问控制

### 1.2【前端】登录后用户数据是伪造的空壳

- 文件：`bio-web/src/pages/Login/index.tsx`
- 登录成功后仅用表单输入的 `username` 拼装了一个空壳 User 对象，`id` 为空、`roles` 为空数组
- 导致 `isAdmin()` 永远返回 `false`，管理员功能完全失效
- 头像、邮箱、积分全部为空或默认值
- 任何依赖 `user.id` 的逻辑都会出错
- **修复方案**：后端提供 `/auth/me` 接口，登录后立即调用获取完整用户数据

### 1.3【前端】管理员路由完全不可达

- 文件：`bio-web/src/components/ProtectedRoute.tsx`
- 由于登录时 `roles` 为空数组，`isAdmin()` 永远为 `false`
- 所有 `/admin/*` 路由都被 `ProtectedRoute` 重定向到首页，管理后台形同虚设
- 依赖问题 1.2 的修复

### 1.4【前端】首页分页逻辑完全错误

- 文件：`bio-web/src/pages/Home/index.tsx`
- 自造分页算法，用当前页数据量反推 total 值
- 后端 API 已返回 `PageResult`（包含 `total`、`pages`），但前端完全没用
- 如果最后一条数据刚好等于 pageSize，会错误地认为还有下一页
- **修复方案**：使用后端 `PageResult.total` 作为 Pagination 的 total

---

## 二、🟠 严重问题（应尽快修复）

### 2.1【后端】`RedisServiceImpl` 缺少 `@Service` 注解

- 会导致 Spring 无法自动注入该 Bean，运行时报错

### 2.2【后端】多处空指针风险

- 多个 Service 方法中，`getOne()` 返回值直接调用 `.setTags()` / `.getCategoryName()` / `.getDelFlag()` 而没有 null 检查
- 当查询不到数据时会抛 `NullPointerException`

### 2.3【后端】N+1 查询问题

- `BiographyServiceImpl` 中查询传记列表时，对每条传记单独查询标签和分类
- 存在严重的 N+1 查询性能问题
- **修复方案**：使用 JOIN 查询或批量查询优化

### 2.4【后端】Elasticsearch 功能不完整

- `EsBiographyServiceImpl.createdById` 方法返回 null，未实现
- ES 搜索功能可能整体不可用

### 2.5【后端】用户活跃统计功能未实现

- `UserActive` 相关的统计递增逻辑从未被调用
- 活跃度功能形同虚设

### 2.6【前端】401 处理使用硬跳转 + 重复处理

- 文件：`bio-web/src/utils/request.ts`
- 在请求和响应拦截器中重复处理 401
- `window.location.href` 硬跳转会触发整页刷新，丢失所有状态
- 多个并发请求同时 401 时会触发多次 logout 和跳转（竞态风险）
- **修复方案**：使用 React Router 的 `navigate('/login')` 或全局事件机制，并添加防抖/锁防止重复处理

### 2.7【前端】响应拦截器设计导致类型系统崩溃

- 文件：`bio-web/src/utils/request.ts`
- 拦截器成功时返回 `response.data`（即 `ApiResult<T>`），但 Axios 类型系统认为返回 `AxiosResponse`
- 导致全项目 **15+ 处 `as any` 强制断言**，类型安全形同虚设
- **修复方案**：重构拦截器，统一 API 返回类型，消除 `as any`

### 2.8【前端】管理后台分类编辑使用 DOM 查询反模式

- 文件：`bio-web/src/pages/Admin/Categories.tsx`
- 通过 `document.querySelector` 获取 DOM input 值，这是 React 反模式
- **修复方案**：使用受控组件或 Form 实例来管理表单状态

---

## 三、🟡 重要问题（建议修复）

### 3.1【后端】邀请码系统未实现

- 后端声明了 `invitationCode` 字段，但注册时并未实际使用邀请码逻辑
- 注册流程中未校验邀请码

### 3.2【后端】缺少接口幂等性保障

- 创建传记、添加评论等写操作没有幂等性保护（如唯一键约束或 token 机制）
- 用户重复提交会产生重复数据

### 3.3【后端】文件上传缺少安全校验

- MinIO 文件上传未做文件类型白名单校验、文件大小限制
- 存在上传恶意文件风险

### 3.4【后端】数据库 SQL 脚本不完整

- `db/biography.sql` 可能缺少部分表定义或索引
- 缺乏数据库版本管理（如 Flyway/Liquibase）

### 3.5【后端】全局异常处理不统一

- 异常返回格式不统一，部分异常直接抛出未封装
- 前端难以统一处理

### 3.6【前端】点赞状态未与后端同步

- 文件：`bio-web/src/pages/BioDetail/index.tsx`
- `liked` 状态由本地 `useState(false)` 管理，页面刷新后丢失
- 没有从后端获取当前用户是否已点赞

### 3.7【前端】评论缺少回复功能

- 后端 `BioComment` 支持 `parentId` 和 `childComment`（嵌套评论）
- 前端只展示平铺列表，没有回复入口和嵌套结构展示

### 3.8【前端】缺少 Error Boundary

- 没有 React Error Boundary 组件
- 任何组件内 JS 错误都会导致整个应用白屏

### 3.9【前端】Token 过期未校验

- 文件：`bio-web/src/components/ProtectedRoute.tsx`
- 只检查 localStorage 中是否有 token，不验证 token 是否过期
- 过期 token 仍能通过路由守卫

### 3.10【前端】Zustand persist 未加密敏感数据

- 文件：`bio-web/src/store/authStore.ts`
- Token 和用户信息以明文存储在 localStorage
- 存在 XSS 攻击窃取 token 的风险

---

## 四、🔵 中等问题（建议优化）

### 4.1【后端】MyBatis-Plus 类型别名包路径不匹配

- dev 配置中的 `type-aliases-package` 与实际包路径不一致
- 可能导致运行时别名解析失败

### 4.2【后端】日志切面可能记录敏感信息

- `WebLogAspect` 记录请求参数，可能包含密码等敏感信息
- 应做脱敏处理

### 4.3【后端】Redis 缓存策略不完善

- 缺少缓存穿透/雪崩/击穿保护
- 缓存 key 过期时间策略不明确

### 4.4【后端】RabbitMQ 配置缺少死信队列和消息确认机制

- 消息消费失败没有重试和死信队列兜底
- 可能丢失消息

### 4.5【后端】Swagger 配置生产环境未关闭

- API 文档在生产环境仍然可访问，存在信息泄露风险

### 4.6【前端】传记摘要截取逻辑粗糙

- 文件：`bio-web/src/pages/Home/index.tsx`
- 简单正则无法正确清理 `![alt](url)`、`[text](url)`、表格语法等 Markdown 语法
- **修复方案**：使用 Markdown 解析库提取纯文本

### 4.7【前端】用户显示名只用 ID 后6位

- `用户 {userId?.slice(-6)}` 非常不友好
- **修复方案**：展示 username 或 nickname

### 4.8【前端】好友管理添加方式不友好

- 文件：`bio-web/src/pages/Dashboard/Friends.tsx`
- 添加好友需要输入"用户ID"，用户几乎不可能知道其他用户的 ID
- **修复方案**：支持用户名搜索或邀请码

### 4.9【前端】无移动端适配

- Dashboard Sider 固定 220px，大量固定宽度样式
- 移动端体验极差

### 4.10【前端】搜索无防抖

- Home 页和 MainLayout 的搜索框都没有防抖
- 每次回车都直接发请求

### 4.11【前端】MDEditor 未按需加载

- 整包引入体积较大
- **修复方案**：使用 `import()` 动态加载

### 4.12【前端】Dashboard Bio 表格分页未使用后端 total

- 文件：`bio-web/src/pages/Dashboard/Bio.tsx`
- Table pagination 的 total 没有使用后端返回的 total 值

### 4.13【前端】ResetPassword 页面 countdown 未使用

- 文件：`bio-web/src/pages/ResetPassword/index.tsx`
- `countdown` state 声明了但从未使用，发送重置邮件按钮没有倒计时

### 4.14【前端】bio-web 目录中混入 Java Gateway 代码

- `bio-web/` 目录下有 `pom.xml` 和 `src/main/java/`
- Java 后端代码混在前端项目中，结构混乱
- **修复方案**：分离项目结构

---

## 五、📋 未完成功能清单

| # | 功能 | 后端状态 | 前端状态 |
|---|------|---------|---------|
| 1 | 邀请码系统 | 字段存在，逻辑未实现 | 未展示/使用 |
| 2 | 积分系统 | 字段存在，逻辑未实现 | 未展示 |
| 3 | 用户活跃度统计 | 模型存在，统计递增未调用 | 无展示页面 |
| 4 | ElasticSearch 搜索 | Service 不完整（createdById 返回 null） | 未对接 ES 搜索 |
| 5 | 嵌套评论/回复 | 模型支持 parentId/childComment | 只展示平铺列表 |
| 6 | 管理后台-已审核评论查看 | API 可能已就绪 | 只看待审核 |
| 7 | 管理后台-标签编辑 | API 可能已就绪 | 只有新增/删除 |
| 8 | 密码修改 | 未确认是否有 API | 无入口 |
| 9 | 用户头像展示 | 上传 API 已有 | 用户资料页未使用 |
| 10 | 标签筛选传记 | 不确定 | 未实现 |
| 11 | 自定义 Hooks | - | hooks 目录为空 |
| 12 | 404 页面 | - | 无，未匹配路由跳首页 |
| 13 | 环境变量配置 | - | 无 .env 文件 |
| 14 | 单元测试 | 只有空测试类 | 无测试库和测试代码 |
| 15 | favicon.svg | - | index.html 引用但文件不存在 |

---

## 六、🏗️ 架构层面建议

| 领域 | 建议 |
|------|------|
| **安全** | 敏感配置迁移到环境变量/配置中心；JWT 密钥加强；接口加权限注解；文件上传加白名单 |
| **数据层** | 引入 Flyway/Liquibase 做数据库版本管理；N+1 改批量查询；缓存加穿透/雪崩保护 |
| **消息队列** | RabbitMQ 加死信队列、消息确认机制、消费幂等性 |
| **前端类型** | 重构响应拦截器，统一 API 返回类型，消除 `as any` |
| **前端状态** | 登录后调 `/auth/me` 获取真实用户数据；Token 过期校验；localStorage 加密 |
| **前端工程** | 添加 Error Boundary、404 页面、移动端适配、防抖搜索、按需加载 MDEditor |
| **测试** | 前后端都缺乏测试，建议后端引入单元测试+集成测试，前端引入 Vitest |
| **部署** | 前端 `bio-web/` 中混入了 Java Gateway 代码，建议分离项目结构 |

---

## 七、优先级执行建议

### P0 — 立即修复（阻塞核心流程）
1. 登录后获取真实用户数据（前端问题 1.2）
2. 管理员路由不可达（前端问题 1.3，依赖 1.2）
3. 首页分页逻辑错误（前端问题 1.4）
4. 敏感配置硬编码（后端问题 1.1）

### P1 — 本迭代修复（影响体验和安全）
5. 接口权限校验缺失（后端问题 1.1 部分）
6. RedisServiceImpl 缺少 @Service（后端问题 2.1）
7. 空指针风险（后端问题 2.2）
8. N+1 查询（后端问题 2.3）
9. 401 处理优化（前端问题 2.6）
10. 类型系统修复（前端问题 2.7）
11. 分类编辑反模式（前端问题 2.8）

### P2 — 下迭代修复（功能完善）
12. 点赞状态持久化（前端问题 3.6）
13. 评论回复功能（前端问题 3.7）
14. Error Boundary（前端问题 3.8）
15. Token 过期校验（前端问题 3.9）
16. 接口幂等性（后端问题 3.2）
17. 文件上传安全（后端问题 3.3）

### P3 — 后续优化（体验提升）
18. 移动端适配（前端问题 4.9）
19. 搜索防抖（前端问题 4.10）
20. 邀请码/积分/活跃度等未完成功能补齐
21. 单元测试补齐
22. 其余中等/低优先级问题

