package com.example.bio.service;

import com.example.bio.model.User;

/**
 * @author zhangfuqi
 * @date 2020/10/25
 */
public interface MailService {

    /**
     * 发送HTML邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendHtmlMessage(String to, String subject, String content);

    /**
     * 发送激活邮件
     *
     * @param token
     * @param user
     */
    void sendActiveMail(User user, String token);
}
