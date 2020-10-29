package com.example.bio.exception;

import com.example.bio.common.api.ErrorCode;

/**
 * 自定义API异常
 *
 * @author zhangfuqi
 * @date 2020/10/28
 */
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
