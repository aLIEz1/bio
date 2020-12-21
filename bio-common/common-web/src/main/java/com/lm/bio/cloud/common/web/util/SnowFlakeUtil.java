package com.lm.bio.cloud.common.web.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author zhangfuqi
 * @date 2020/12/21
 */
public class SnowFlakeUtil {
    private static final Snowflake SNOWFLAKE = IdUtil.createSnowflake(1, 1);

    public static Long nextId() {
        return SNOWFLAKE.nextId();
    }
}
