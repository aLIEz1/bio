package com.example.bio.common.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangfuqi
 * @date 2020/11/5
 */
public interface DistributedLockTemplate {
    /**
     * 执行方法
     *
     * @param lockId    锁id（对应唯一业务ID）
     * @param timeout   最大等待获取锁时间
     * @param leaseTime 最长占用锁时间 <=0或null时将启用看门狗机制（程序未执行完自动续期锁）
     * @param unit      时间单位
     * @param callback  回调方法
     * @return object
     */
    Object execute(String lockId, Integer timeout, Integer leaseTime, TimeUnit unit, Callback callback);
}
