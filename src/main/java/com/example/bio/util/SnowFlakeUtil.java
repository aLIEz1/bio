package com.example.bio.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author zhangfuqi
 * @date 2020/10/30
 */
public class SnowFlakeUtil {
    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static Long nextId() {
        return snowflake.nextId();
    }
}
