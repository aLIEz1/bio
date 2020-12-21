package com.lm.bio.cloud.common.core.api;

/**
 * 错误码
 *
 * @author zhangfuqi
 * @date 2020/12/21
 */
public interface ResultCode {
    /**
     * 获取code
     *
     * @return code
     */
    Integer getCode();

    /**
     * 获取msg
     *
     * @return message
     */
    String getMessage();
}
