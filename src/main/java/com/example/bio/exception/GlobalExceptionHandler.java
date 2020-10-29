package com.example.bio.exception;

import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author zhangfuqi
 * @date 2020/10/28
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(value = ApiException.class)
    public Result<?> handle(ApiException e) {
        if (e.getErrorCode() == null) {
            return fail(e.getErrorCode());
        }
        return fail(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e){
        return getResult(e.getBindingResult());
    }

    @ExceptionHandler(value = BindException.class)
    public Result<?> handleValidException(BindException e){
        return getResult(e.getBindingResult());
    }

    private Result<?> getResult(BindingResult bindingResult) {
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return validateFailed(message);
    }
}
