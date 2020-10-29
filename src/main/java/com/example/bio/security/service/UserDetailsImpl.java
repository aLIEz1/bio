package com.example.bio.security.service;

import com.example.bio.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangfuqi
 * @date 2020/10/26
 */
public class UserDetailsImpl implements UserDetails {
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private Boolean nonLocked;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, String email, Boolean nonLocked, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nonLocked = nonLocked;
        this.authorities = authorities;
    }

    public static UserDetailsImpl builder(User user, List<GrantedAuthority> authorities) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getIsLocked() == 0,
                authorities
        );
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public Boolean getNonLocked() {
        return nonLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
