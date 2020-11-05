package com.example.bio.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LimitException extends RuntimeException {
    private String msg;

    public LimitException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
