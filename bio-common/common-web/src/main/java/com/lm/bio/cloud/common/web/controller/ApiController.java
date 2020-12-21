package com.lm.bio.cloud.common.web.controller;

import com.lm.bio.cloud.common.core.api.CommonResult;
import com.lm.bio.cloud.common.core.api.ResultCode;
import com.lm.bio.cloud.common.core.api.ResultEnum;
import com.lm.bio.cloud.common.core.util.ResultUtil;

/**
 * @author zhangfuqi
 * @date 2020/12/21
 */
public class ApiController {
    protected CommonResult<Object> ok() {
        return ResultUtil.buildSuccess();
    }


    protected <T> CommonResult<T> ok(T data) {
        return ResultUtil.buildSuccess(data);
    }

    protected CommonResult<Object> ok(String msg) {
        return ResultUtil.buildSuccess(msg);
    }

    protected CommonResult<Object> ok(ResultCode resultCode) {
        return ResultUtil.buildSuccess(resultCode);
    }

    protected <T> CommonResult<T> ok(ResultCode resultCode, T data) {
        return ResultUtil.buildSuccess(resultCode, data);
    }

    protected <T> CommonResult<T> ok(Integer code, String msg, T data) {
        return ResultUtil.buildSuccess(code, msg, data);
    }


    protected CommonResult<Object> fail() {
        return ResultUtil.buildError();
    }

    protected CommonResult<Object> fail(String msg) {
        return ResultUtil.buildError(msg);
    }

    protected CommonResult<Object> fail(ResultCode resultCode) {
        return ResultUtil.buildError(resultCode);
    }

    protected <T> CommonResult<T> fail(Integer code, String msg, T data) {
        return ResultUtil.buildError(code, msg, data);
    }

    protected <T> CommonResult<T> fail(ResultCode resultCode, T data) {
        return ResultUtil.buildError(resultCode, data);
    }

    /**
     * 参数验证失败返回结果
     *
     * @return VALIDATE_FAILED
     */
    protected CommonResult<Object> validateFailed() {
        return ResultUtil.buildError(ResultEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     * @return VALIDATE_FAILED
     */
    protected CommonResult<Object> validateFailed(String message) {
        return ResultUtil.buildError(ResultEnum.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     *
     * @param data 数据
     * @param <T> 数据类型
     * @return Result
     */
    protected <T> CommonResult<T> unauthorized(T data) {
        return ResultUtil.buildError(ResultEnum.UNAUTHORIZED.getCode(), ResultEnum.UNAUTHORIZED.getMessage(), data);
    }


    /**
     * 未授权返回结果
     *
     * @param data 数据
     * @param <T> 数据类型
     * @return Result
     */
    protected <T> CommonResult<T> forbidden(T data) {
        return ResultUtil.buildError(ResultEnum.FORBIDDEN.getCode(), ResultEnum.FORBIDDEN.getMessage(), data);
    }
}
