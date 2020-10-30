package com.example.bio.security.component;

import cn.hutool.json.JSONUtil;
import com.example.bio.common.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义返回结果：未登录或登录过期
 *
 * @author zhangfuqi
 * @date 2020/10/28
 */
@Slf4j
public class RestAuthenticationEntryPoint extends BaseController implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        log.info(authException.getMessage());
        response.getWriter().println(JSONUtil.parse(unauthorized(authException.getMessage())));
        response.getWriter().flush();
    }
}
