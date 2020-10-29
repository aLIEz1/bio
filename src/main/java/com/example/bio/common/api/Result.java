package com.example.bio.common.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author super
 */
@NoArgsConstructor
@Getter
@Setter
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long code;

    private String msg;

    private T data;


    public Result(Long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Long code, String msg) {
        this(code, msg, null);
    }

    public Result(ErrorCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public Result(ErrorCode resultCode, T data) {
        this(resultCode.getCode(), resultCode.getMessage(), data);
    }
}
