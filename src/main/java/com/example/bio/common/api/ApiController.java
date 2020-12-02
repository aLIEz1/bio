package com.example.bio.common.api;

/**
 * @author super
 */
public class ApiController {
    protected Result<Object> ok() {
        return ResultUtil.buildSuccess();
    }


    protected <T> Result<T> ok(T data) {
        return ResultUtil.buildSuccess(data);
    }

    protected Result<Object> ok(String msg) {
        return ResultUtil.buildSuccess(msg);
    }

    protected Result<Object> ok(ErrorCode resultCode) {
        return ResultUtil.buildSuccess(resultCode);
    }

    protected <T> Result<T> ok(ErrorCode resultCode, T data) {
        return ResultUtil.buildSuccess(resultCode, data);
    }

    protected <T> Result<T> ok(Long code, String msg, T data) {
        return ResultUtil.buildSuccess(code, msg, data);
    }


    protected Result<Object> fail() {
        return ResultUtil.buildError();
    }

    protected Result<Object> fail(String msg) {
        return ResultUtil.buildError(msg);
    }

    protected Result<Object> fail(ErrorCode resultCode) {
        return ResultUtil.buildError(resultCode);
    }

    protected <T> Result<T> fail(Long code, String msg, T data) {
        return ResultUtil.buildError(code, msg, data);
    }

    protected <T> Result<T> fail(ErrorCode resultCode, T data) {
        return ResultUtil.buildError(resultCode, data);
    }

    /**
     * 参数验证失败返回结果
     *
     * @return VALIDATE_FAILED
     */
    protected Result<Object> validateFailed() {
        return ResultUtil.buildError(EResult.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     * @return VALIDATE_FAILED
     */
    protected Result<Object> validateFailed(String message) {
        return ResultUtil.buildError(EResult.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     *
     * @param data
     * @param <T>
     * @return
     */
    protected <T> Result<T> unauthorized(T data) {
        return ResultUtil.buildError(EResult.UNAUTHORIZED.getCode(), EResult.UNAUTHORIZED.getMessage(), data);
    }


    /**
     * 未授权返回结果
     *
     * @param data
     * @param <T>
     * @return
     */
    protected <T> Result<T> forbidden(T data) {
        return ResultUtil.buildError(EResult.FORBIDDEN.getCode(), EResult.FORBIDDEN.getMessage(), data);
    }

}
