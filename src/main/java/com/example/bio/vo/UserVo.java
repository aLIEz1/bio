package com.example.bio.vo;

import com.example.bio.model.Role;

import java.util.Set;

/**
 * 用户信息视图对象，用于 /auth/me 接口返回
 *
 * @author bio
 */
public class UserVo {

    private String id;
    private String username;
    private String avatar;
    private String email;
    private String invitationCode;
    private Integer points;
    private Integer isLocked;
    private Set<Role> roles;

    public UserVo() {
    }

    public UserVo(String id, String username, String avatar, String email,
                  String invitationCode, Integer points, Integer isLocked, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.invitationCode = invitationCode;
        this.points = points;
        this.isLocked = isLocked;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

