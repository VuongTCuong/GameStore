package com.gamestore.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gamestore.gamestore.config.UserPrinciple;
import com.gamestore.gamestore.entity.User;
import com.gamestore.gamestore.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepo.findByAccount(username).orElseThrow(()-> new UsernameNotFoundException("Người dùng không tồn tại"));
        return new UserPrinciple(user);
    }
}
