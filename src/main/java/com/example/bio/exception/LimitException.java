package com.example.bio.exception;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
public class LimitException extends RuntimeException {
    private String msg;

    public LimitException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
}
