package com.example.bio.common.constant;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
public interface CommonConstant {

    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "https://ooo.0o0.ooo/2019/04/28/5cc5a71a6e3b6.png";
    /**
     * 限流前缀
     */
    String LIMIT_PRE = "BIO_LIMIT:";

    /**
     * 限流标识
     */
    String LIMIT_ALL = "BIO_LIMIT_ALL";

    /**
     * 邮箱激活码链接
     */
    String ACTIVE_MAIL_URL = "http://192.168.31.24:8082/api/auth/active?token=";
}
