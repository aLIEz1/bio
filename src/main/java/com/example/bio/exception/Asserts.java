package com.example.bio.exception;

import com.example.bio.common.api.ErrorCode;

/**
 * 断言处理类，处理API异常
 * @author zhangfuqi
 * @date 2020/10/28
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(ErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
