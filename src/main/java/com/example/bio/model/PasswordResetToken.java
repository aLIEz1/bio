package com.example.bio.model;

import java.io.Serializable;

public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String token;

    public PasswordResetToken() {}

    public PasswordResetToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
