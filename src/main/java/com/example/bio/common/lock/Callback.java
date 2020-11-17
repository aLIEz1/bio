package com.example.bio.common.lock;

import java.io.IOException;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
public interface Callback {

    /**
     * 成功获取锁后执行方法
     *
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    Object onGetLock() throws InterruptedException, IOException;

    /**
     * 获取锁超时回调
     *
     * @return
     * @throws InterruptedException
     */
    Object onTimeout() throws InterruptedException;
}
