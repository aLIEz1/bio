package com.lm.bio.cloud.common.web.exception;

import com.lm.bio.cloud.common.core.api.ResultCode;
import lombok.Getter;

/**
 * @copyright (C), Since 2021
 * @author: lm
 * @date: 2021/1/23 19:06
 * @description: 业务异常类
 */
@Getter
public class BizException extends RuntimeException{
    private ResultCode resultCode;

    public BizException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BizException(String message){
        super(message);
    }

    public BizException(String message, Throwable cause){
        super(message, cause);
    }

    public BizException(Throwable cause){
        super(cause);
    }
}
