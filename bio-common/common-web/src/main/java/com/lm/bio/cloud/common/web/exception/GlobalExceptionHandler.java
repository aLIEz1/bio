package com.lm.bio.cloud.common.web.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lm.bio.cloud.common.core.api.CommonResult;
import com.lm.bio.cloud.common.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @copyright (C), Since 2021
 * @author: lm
 * @date: 2021/1/23 19:08
 * @description: 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseController {
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常，异常原因：{}", e.getMessage(), e);
        return fail(e.getMessage());
    }


    @ExceptionHandler(JsonProcessingException.class)
    public CommonResult<Object> handleJsonProcessingException(JsonProcessingException e) {
        log.error("Json转换异常，异常原因：{}", e.getMessage(), e);
        return fail(e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    public CommonResult<Object> handleBizException(BizException e) {
        log.error("业务异常，异常原因：{}", e.getMessage(), e);
        if (e.getResultCode() != null) {
            return fail(e.getResultCode());
        }
        return fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<Object> handleException(Exception e) {
        log.error("未知异常，异常原因：{}", e.getMessage(), e);
        return fail(e.getMessage());
    }
}
