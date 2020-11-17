package com.example.bio.dto;

import com.example.bio.common.constraint.FieldMatch;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangfuqi
 * @date 2020/11/16
 */
@FieldMatch(first = "password", second = "confirmationPassword", message = "两次输入必须一致")
public class ResetPasswordDto {

    @NotBlank
    private String emailAddress;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmationPassword;
    @NotBlank
    private String tokenSecret;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
