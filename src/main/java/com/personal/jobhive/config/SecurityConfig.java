package com.personal.jobhive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.personal.jobhive.services.SecurityCustomDetailService;

@Configuration
public class SecurityConfig {
    // create user  and login using java code with in-memory service
    /*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
        .withDefaultPasswordEncoder()
        .username("admin")
        .password("admin")
        .roles("ADMIN","USER")
        .build();

        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
        return inMemoryUserDetailsManager;
    }
    */

    @Autowired
    private SecurityCustomDetailService securityCustomDetailService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securityCustomDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}
