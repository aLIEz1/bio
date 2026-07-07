package com.example.bio.user.infrastructure.gateway;

import com.example.bio.dto.ResetPasswordDto;
import com.example.bio.dto.SignupDto;
import com.example.bio.model.User;
import com.example.bio.service.UserService;
import com.example.bio.user.domain.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserGatewayImpl implements UserGateway {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getByUsername(String username) {
        return userService.getOneByUsername(username);
    }

    @Override
    public User getById(String id) {
        return userService.getById(id);
    }

    @Override
    public boolean unlockUser(String token) {
        return userService.unlockUser(token);
    }

    @Override
    public void registerUser(SignupDto signupDto) {
        userService.registerUser(signupDto);
    }

    @Override
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @Override
    public String generateAuthCode(String email) {
        return userService.generateAuthCode(email);
    }

    @Override
    public void getResetPasswordToken(String email) {
        userService.getResetPasswordToken(email);
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        userService.resetPassword(resetPasswordDto);
    }
}
