package com.lm.bio.cloud.common.core.util;

import com.lm.bio.cloud.common.core.api.CommonResult;
import com.lm.bio.cloud.common.core.api.ResultCode;
import com.lm.bio.cloud.common.core.api.ResultEnum;

/**
 * 结果集工具类
 *
 * @author zhangfuqi
 * @date 2020/12/21
 */
public class ResultUtil {
    public static <T> CommonResult<T> buildSuccess(T data) {
        return new CommonResult<>(ResultEnum.SUCCESS, data);
    }

    public static <T> CommonResult<T> buildSuccess(ResultCode resultCode, T data) {
        return new CommonResult<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static CommonResult<Object> buildSuccess() {
        return new CommonResult<>(ResultEnum.SUCCESS);
    }

    public static CommonResult<Object> buildSuccess(String message) {
        return new CommonResult<>(ResultEnum.SUCCESS.getCode(), message);
    }

    public static CommonResult<Object> buildSuccess(Integer code, String msg) {
        return new CommonResult<>(code, msg);
    }

    public static CommonResult<Object> buildSuccess(ResultCode resultCode) {
        return new CommonResult<>(resultCode);
    }

    public static <T> CommonResult<T> buildSuccess(Integer code, String msg, T data) {
        return new CommonResult<>(code, msg, data);
    }


    public static <T> CommonResult<T> buildError(T data) {
        return new CommonResult<>(ResultEnum.FAILED, data);
    }

    public static CommonResult<Object> buildError() {
        return new CommonResult<>(ResultEnum.FAILED);
    }

    public static CommonResult<Object> buildError(String message) {
        return new CommonResult<>(ResultEnum.FAILED.getCode(), message);
    }

    public static CommonResult<Object> buildError(Integer code, String msg) {
        return new CommonResult<>(code, msg);
    }

    public static <T> CommonResult<T> buildError(Integer code, String msg, T data) {
        return new CommonResult<T>(code, msg, data);
    }

    public static <T> CommonResult<T> buildError(ResultCode resultCode, T data) {
        return new CommonResult<T>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static CommonResult<Object> buildError(ResultCode resultCode) {
        return new CommonResult<>(resultCode);
    }
}
