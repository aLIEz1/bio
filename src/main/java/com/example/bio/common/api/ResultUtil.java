package com.example.bio.common.api;

import lombok.extern.slf4j.Slf4j;

/**
 * @author super
 */
@Slf4j
public class ResultUtil {

    public static <T> Result buildSuccess(T data) {
        return new Result<T>(EResult.SUCCESS, data);
    }

    public static <T> Result buildSuccess(ErrorCode resultCode, T data) {
        return new Result<T>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static Result buildSuccess() {
        return new Result<>(EResult.SUCCESS);
    }

    public static Result buildSuccess(String msg) {
        return new Result<>(EResult.SUCCESS.getCode(), msg);
    }

    public static Result buildSuccess(Long code, String msg) {
        return new Result<>(code, msg);
    }

    public static Result buildSuccess(ErrorCode resultCode) {
        return new Result<>(resultCode);
    }

    public static <T> Result buildError(T data) {
        return new Result<T>(EResult.FAILED, data);
    }

    public static Result buildError() {
        return new Result<>(EResult.FAILED);
    }

    public static Result buildError(String msg) {
        return new Result<>(EResult.FAILED.getCode(), msg);
    }

    public static Result buildError(Long code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result buildError(Long code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

    public static <T> Result buildError(ErrorCode resultCode, T data) {
        return new Result<T>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static Result buildError(ErrorCode resultCode) {
        return new Result<>(resultCode);
    }

    public static <T> Result buildSuccess(Long code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}
