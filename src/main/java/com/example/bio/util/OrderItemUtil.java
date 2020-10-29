package com.example.bio.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangfuqi
 * @date 2020/10/29
 */
public class OrderItemUtil {
    public static List<OrderItem> orderHandler(Map<String, Object> conditions){
        String orderBy = (String) conditions.get("orderBy");
        Boolean isAsc = (Boolean) conditions.get("isAsc");
        List<OrderItem> orderItems = new ArrayList<>();
        if (StrUtil.isNotBlank(orderBy)) {
            String[] split = StrUtil.split(orderBy, ",");
            if (ObjectUtil.isNotNull(isAsc) && isAsc) {
                for (String s : split) {
                    orderItems.add(OrderItem.asc(s));
                }
            } else {
                for (String s : split) {
                    orderItems.add(OrderItem.desc(s));
                }
            }
        } else {
            orderItems.add(OrderItem.desc("gmt_create"));
        }
        return orderItems;
    }
}
