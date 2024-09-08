package com.personal.jobhive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.personal.jobhive.repositories.UserRepo;

@Service
public class SecurityCustomDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // load your user
        return userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
    
}
