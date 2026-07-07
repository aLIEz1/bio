package com.example.bio.common.api;

import java.io.Serializable;

/**
 * @author super
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long code;
    private String msg;
    private T data;

    public Result() {}

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

    public Long getCode() { return code; }
    public void setCode(Long code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    @Override
    public String toString() {
        return "Result{code=" + code + ", msg='" + msg + "', data=" + data + "}";
    }
}
