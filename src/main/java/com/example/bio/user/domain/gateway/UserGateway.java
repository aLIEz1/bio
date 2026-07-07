package com.example.bio.user.domain.gateway;

import com.example.bio.model.User;

public interface UserGateway {
    User getByUsername(String username);
    User getById(String id);
    boolean unlockUser(String token);
    void registerUser(com.example.bio.dto.SignupDto signupDto);
    User getCurrentUser();
    String generateAuthCode(String email);
    void getResetPasswordToken(String email);
    void resetPassword(com.example.bio.dto.ResetPasswordDto resetPasswordDto);
}
