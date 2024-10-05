package com.lezhintask.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 유저 정의 UserDetails 구현
 */
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private boolean isAdult;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, boolean isAdult, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.isAdult = isAdult;
        this.authorities = authorities;
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

    public boolean isAdult() {
        return isAdult;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
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