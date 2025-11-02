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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamestore.gamestore.dto.CreateCartDetailDTO;
import com.gamestore.gamestore.entity.CartDetail;
import com.gamestore.gamestore.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/get")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CartDetail>> getCartDetailbyUser(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(cartService.getCartDetailbyUser(userDetails));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String,Object>> addtoCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateCartDetailDTO createCartDetailDTO){
        return ResponseEntity.ok().body(cartService.addtoCart(userDetails, createCartDetailDTO));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String,Object>> updateCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateCartDetailDTO updateCartDetailDTO){
        return ResponseEntity.ok().body(cartService.updateCart(userDetails, updateCartDetailDTO));
    }

    @DeleteMapping("/delete/{gameID}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String,Object>> deleteCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer gameID){
        return ResponseEntity.ok().body(cartService.deleteCart(userDetails, gameID));
    }
}
