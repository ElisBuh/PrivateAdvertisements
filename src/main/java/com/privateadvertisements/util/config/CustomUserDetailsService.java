package com.privateadvertisements.util.config;


import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private IUserService userService;

    public CustomUserDetailsService(IUserService userService) {
        this.userService = userService;
    }


    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info(s);
        User user = userService.findByUserLogin(s);
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }
}
