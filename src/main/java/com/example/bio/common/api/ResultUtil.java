package com.example.bio.common.api;

import lombok.extern.slf4j.Slf4j;

/**
 * @author super
 */
@Slf4j
public class ResultUtil {

    public static <T> Result<T> buildSuccess(T data) {
        return new Result<>(EResult.SUCCESS, data);
    }

    public static <T> Result<T> buildSuccess(ErrorCode resultCode, T data) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static Result<Object> buildSuccess() {
        return new Result<>(EResult.SUCCESS);
    }

    public static Result<Object> buildSuccess(String msg) {
        return new Result<>(EResult.SUCCESS.getCode(), msg);
    }

    public static Result<Object> buildSuccess(Long code, String msg) {
        return new Result<>(code, msg);
    }

    public static Result<Object> buildSuccess(ErrorCode resultCode) {
        return new Result<>(resultCode);
    }

    public static <T> Result<T> buildError(T data) {
        return new Result<>(EResult.FAILED, data);
    }

    public static Result<Object> buildError() {
        return new Result<>(EResult.FAILED);
    }

    public static Result<Object> buildError(String msg) {
        return new Result<>(EResult.FAILED.getCode(), msg);
    }

    public static Result<Object> buildError(Long code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> buildError(Long code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

    public static <T> Result<T> buildError(ErrorCode resultCode, T data) {
        return new Result<T>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static Result<Object> buildError(ErrorCode resultCode) {
        return new Result<>(resultCode);
    }

    public static <T> Result<T> buildSuccess(Long code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}
