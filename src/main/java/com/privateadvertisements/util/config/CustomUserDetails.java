package com.privateadvertisements.util.config;

import com.privateadvertisements.model.Role;
import com.privateadvertisements.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetails.class);

    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public static CustomUserDetails fromUserToCustomUserDetails(User user) {
        log.info(user.getLogin());
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.login = user.getLogin();
        userDetails.password = user.getPasswords();
        for (Role role : user.getRoles()) {
            userDetails.grantedAuthorities = Collections.singletonList((new SimpleGrantedAuthority(role.getName())));
        }
        return userDetails;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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
