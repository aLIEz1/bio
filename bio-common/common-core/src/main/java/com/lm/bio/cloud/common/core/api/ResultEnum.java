package com.lm.bio.cloud.common.core.api;

/**
 * 返回结果封装枚举类
 *
 * @author zhangfuqi
 * @date 2020/12/21
 */
public enum ResultEnum implements ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(201, "用户不存在"),
    /**
     * 用户名或者密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(210, "用户名或密码错误"),

    /**
     * 客户端认证失败
     */
    CLIENT_AUTHENTICATION_FAILED(212, "客户端认证失败"),
    /**
     * token无效或已过期
     */
    TOKEN_INVALID_OR_EXPIRED(230, "token无效或已过期"),
    /**
     * 访问未授权
     */
    USER_ACCESS_UNAUTHORIZED(301, "访问未授权"),

    /**
     * 参数校验失败
     */
    VALIDATE_FAILED(404, "参数检验失败"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限");

    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
