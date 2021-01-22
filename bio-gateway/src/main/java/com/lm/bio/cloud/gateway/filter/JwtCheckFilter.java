package com.lm.bio.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.lm.bio.cloud.common.redis.service.RedisService;
import com.lm.bio.cloud.gateway.security.domain.WhiteListProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhangfuqi
 * @date 2021/1/1
 */
@Component
@AllArgsConstructor
@EnableConfigurationProperties(WhiteListProperties.class)
public class JwtCheckFilter implements GlobalFilter, Ordered {

    private final RedisService redisService;

    private final WhiteListProperties whiteListProperties;

    /**
     * 过滤器拦截到用户请求之后该做什么
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        1.该接口是否需要token访问,不需要token直接放行
        if (isRequireToken(exchange)) {
            return chain.filter(exchange);
        }
//        2.取出用户的token
        String token = getUserToken(exchange);
//        3.判断用户的token是否有效
        if (StrUtil.isBlank(token)) {
            return buildNoAuthorizationResult(exchange);
        }
        Boolean hasKey = redisService.hasKey(token);
//        token有效直接放行
        if (hasKey != null && hasKey) {
            return chain.filter(exchange);
        }
        return buildNoAuthorizationResult(exchange);

//        自顶向下的方法编程
    }

    /**
     * 给用户响应一个没有token的错误
     *
     * @param exchange
     * @return
     */
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "NoAuthorization");
        jsonObject.put("errorMessage", "Token is null or error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));

    }

    /**
     * 从请求头里获取用户token
     *
     * @param exchange
     * @return
     */
    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replace("bearer ", "");
    }


    /**
     * 判断接口是否需要判断
     *
     * @param exchange
     * @return
     */
    private boolean isRequireToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        return whiteListProperties.getNoRequireTokenUris().contains(path);
    }

    /**
     * 拦截器的顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
