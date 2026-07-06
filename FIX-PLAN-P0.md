# Bio 项目 P0 致命问题修复计划

> 创建日期：2026-07-03
> 范围：4 项 P0 致命问题

---

## 关键发现

| 发现 | 说明 |
|------|------|
| `application-prod.yml` 已环境变量化 | 只需改 `application.yml`（dev 配置） |
| `.gitignore` 已包含 `.env` | 无需再添加 |
| ERole 枚举值 | `ROLE_ADMIN` / `ROLE_USER` / `ROLE_COMPANY`，与前端 `isAdmin()` 一致 |
| 后端 signIn 已返回完整 JwtVo | 含 accessToken, id, username, email, roles；但前端声明为 `ApiResult<string>` 丢弃了用户数据 |
| App.tsx 无启动恢复逻辑 | 页面刷新后登录态无法恢复（需新增 /auth/me 调用） |

---

## P0-1: 敏感配置硬编码 + JWT 密钥弱

### 现状

`application.yml` 中硬编码了：
- 数据库密码 `74521`
- Redis 密码（空但显式写死）
- RabbitMQ 用户名/密码 `guest/guest`
- JWT 密钥 `SecretKey`（过短过弱）
- MinIO 凭证 `minioadmin/minioadmin`
- 邮箱密码 `zfwogjvrjtsucgda`
- Druid 监控密码 `admin/123456`

### 修复步骤

1. **创建 `bio/.env` 文件**，包含所有敏感配置的键值对
2. **修改 `bio/src/main/resources/application.yml`**，用 `${ENV_VAR:default}` 替换硬编码值
3. **生成 JWT 强密钥**（至少 256 位），写入 `.env` 的 `JWT_SECRET`

### 涉及文件

| 文件 | 操作 |
|------|------|
| `bio/.env` | 新建 |
| `bio/src/main/resources/application.yml` | 修改 |

---

## P0-2: 登录后用户数据伪造

### 现状

- 后端 `AuthController.signIn` 返回 `JwtVo(accessToken, id, username, email, roles)`
- 前端 `signIn` 声明返回 `ApiResult<string>`，只取 `res.data` 当 token
- `Login/index.tsx` 用 `values.username` 拼凑空壳 User：id 为空、roles 为空数组
- 导致 `isAdmin()` 永远返回 false，所有依赖 `user.id` 的逻辑出错

### 修复方案：利用已有 JwtVo + 新增 /auth/me

登录时直接从 JwtVo 提取用户数据，同时提供 `/auth/me` 接口用于页面刷新后恢复会话。

### 后端修改

1. **`AuthController.java`** — 新增 `GET /api/auth/me` 接口
   - 从 `SecurityContextHolder` 获取当前 `UserDetailsImpl`
   - 查询完整 User 信息（含 roles），返回 `Result<UserVo>`
   - 新增 `UserVo` 类，包含 id/username/email/avatar/points/isLocked/invitationCode/roles

2. **安全白名单精确化** — 当前白名单 `/api/auth/**` 会覆盖 `/api/auth/me`，需要将白名单改为精确路径列表，排除 `/api/auth/me`

### 前端修改

3. **`bio-web/src/types/index.ts`** — 新增 `JwtVo` 接口
   ```ts
   export interface JwtVo {
     accessToken: string
     tokenType: string
     id: string
     username: string
     email: string
     roles: string[]
   }
   ```

4. **`bio-web/src/api/auth.ts`**
   - `signIn` 返回类型从 `ApiResult<string>` 改为 `ApiResult<JwtVo>`
   - 新增 `getMe(): Promise<ApiResult<User>>` 函数

5. **`bio-web/src/pages/Login/index.tsx`**
   - 从 `res.data` 中提取 `accessToken` 和用户信息
   - 将 `roles: string[]`（如 `["ROLE_ADMIN"]`）映射为前端 `User.roles: Role[]`（如 `[{id:'', roleName:'ROLE_ADMIN'}]`）
   - 构造完整 User 对象

6. **`bio-web/src/store/authStore.ts`** — 新增 `fetchUser` action，调用 `getMe()` 刷新用户数据

7. **`bio-web/src/App.tsx`** — 应用初始化时，若 token 存在但 user.roles 为空，调 `fetchUser` 恢复登录态

### 涉及文件

| 文件 | 操作 |
|------|------|
| `bio/src/main/java/com/example/bio/controller/AuthController.java` | 修改 |
| `bio/src/main/java/com/example/bio/vo/UserVo.java` | 新建 |
| `bio/src/main/resources/application.yml` | 修改（白名单精确化） |
| `bio-web/src/types/index.ts` | 修改 |
| `bio-web/src/api/auth.ts` | 修改 |
| `bio-web/src/pages/Login/index.tsx` | 修改 |
| `bio-web/src/store/authStore.ts` | 修改 |
| `bio-web/src/App.tsx` | 修改 |

---

## P0-3: 管理员路由不可达

### 现状

- `authStore.isAdmin()` 检查 `user.roles.some(r => r.roleName === 'ROLE_ADMIN')`
- 登录时 roles 被设为空数组，所以永远返回 false
- 所有 `/admin/*` 路由被 `ProtectedRoute` 重定向到首页

### 修复方案

**随 P0-2 修复自动解决。**

当 P0-2 修复后：
- 登录时 `JwtVo.roles` 会被正确映射到 `User.roles`
- `isAdmin()` 能正确判断
- `ProtectedRoute` 的 `requireAdmin` 检查会正常工作

### 验证项

- 确认前端 `isAdmin()` 检查 `r.roleName === 'ROLE_ADMIN'` 与后端 `ERole.ROLE_ADMIN` 一致 -- 已确认一致
- 测试管理员账号登录后可正常访问 `/admin/*` 路由

---

## P0-4: 首页分页逻辑完全错误

### 现状

`Home/index.tsx` 第 136 行：
```ts
total={biographies.length < pageSize
  ? (page - 1) * pageSize + biographies.length
  : page * pageSize + 1}
```

- 自造算法，用当前页数据量反推 total
- 后端 `PageResult` 已有 `total`、`pages` 字段
- 如果最后一条数据刚好等于 pageSize，会错误地认为还有下一页

### 修复步骤

1. **修改 `bio-web/src/pages/Home/index.tsx`**
   - 从 `PageResult` 中提取 `records`（传记列表）和 `total`（总数）
   - `Pagination` 的 `total` 直接使用 `PageResult.total`

2. **确认 `getPublicBiographies` 返回类型**为 `ApiResult<PageResult<Biography>>`

### 涉及文件

| 文件 | 操作 |
|------|------|
| `bio-web/src/pages/Home/index.tsx` | 修改 |
| `bio-web/src/api/biography.ts` | 确认/修改返回类型 |

---

## 执行顺序

```
P0-1（后端配置）──┐
                   ├── 可并行
P0-4（分页逻辑）──┘
        │
        ▼
P0-2（登录数据，核心修复，前后端联动）
        │
        ▼
P0-3（验证管理员路由）
```

1. P0-1 + P0-4 可并行（互不依赖）
2. P0-2 是核心修复，需前后端联动
3. P0-2 完成后，P0-3 只需验证

---

## 风险与注意事项

1. **白名单精确化**：将 `/api/auth/**` 改为精确路径列表时，需确保不遗漏现有公开接口
2. **JWT 密钥更换**：更换密钥后，所有已登录用户的 token 将失效，需重新登录
3. **前端 JwtVo 映射**：后端 `JwtVo.roles` 是 `List<String>`（如 `["ROLE_ADMIN"]`），需正确映射为前端 `User.roles: Role[]`（如 `[{id:'', roleName:'ROLE_ADMIN'}]`）

