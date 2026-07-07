package com.example.bio.user.application.command;

import com.alibaba.cola.dto.Command;

public class ResetPasswordCmd extends Command {
    private String emailAddress;
    private String tokenSecret;
    private String password;
    private String confirmationPassword;

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public String getTokenSecret() { return tokenSecret; }
    public void setTokenSecret(String tokenSecret) { this.tokenSecret = tokenSecret; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmationPassword() { return confirmationPassword; }
    public void setConfirmationPassword(String v) { this.confirmationPassword = v; }
}
