package com.example.bio.common.api;

/**
 * @author super
 */
public interface ErrorCode {
    /**
     * 获取code
     *
     * @return code
     */
    long getCode();

    /**
     * 获取msg
     *
     * @return message
     */
    String getMessage();
}
