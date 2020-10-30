package com.example.bio.security.component;

import cn.hutool.json.JSONUtil;
import com.example.bio.common.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义返回结果：没有权限访问时
 *
 * @author zhangfuqi
 * @date 2020/10/28
 */
@Slf4j
public class RestfulAccessDeniedHandler extends BaseController implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        log.info(e.getMessage());
        response.getWriter().println(JSONUtil.parse(forbidden(e.getMessage())));
        response.getWriter().flush();
    }
}
