package com.example.bio.common.api;

/**
 * @author super
 */
public interface ErrorCode {
    /**
     * 获取code
     *
     * @return
     */
    long getCode();

    /**
     * 获取msg
     *
     * @return
     */
    String getMessage();
}
