package com.example.bio.common.constant;

/**
 * @author zhangfuqi
 * @date 2020/11/10
 */
public enum EQueue {
    /**
     * 消息通知队列
     */
    QUEUE_MAIL("bio.mail.direct", "bio.mail", "bio.queue.mail");
    /**
     * 交换名称
     */
    private final String exchange;
    /**
     * 队列名称
     */
    private final String name;
    /**
     * 路由键
     */
    private final String routeKey;

    EQueue(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

    public String getExchange() {
        return exchange;
    }

    public String getName() {
        return name;
    }

    public String getRouteKey() {
        return routeKey;
    }
}
