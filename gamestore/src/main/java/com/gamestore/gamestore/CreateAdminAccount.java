package com.gamestore.gamestore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CreateAdminAccount {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password="123456";
        System.out.println(bCryptPasswordEncoder.encode(password));
    }
}
