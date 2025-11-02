package com.gamestore.gamestore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.gamestore.gamestore.dto.GetUserDTO;
import com.gamestore.gamestore.dto.UpdateUserDTO;
import com.gamestore.gamestore.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String,Object>> getUserInfo(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(userService.getUserInfo(userDetails));
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String,Object>> updateUserInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserDTO updateUser){
        return ResponseEntity.ok().body(userService.updateUserInfo(userDetails, updateUser));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GetUserDTO>> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @PutMapping("/update_state")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> updateUserState(String account, String state){
        return ResponseEntity.ok().body(userService.updateUserState(account, state));
    }

    @DeleteMapping("/delete/{userID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> deleteUser(@PathVariable Integer userID){
        return ResponseEntity.ok().body(userService.deleteUser(userID));
    }
   
}
