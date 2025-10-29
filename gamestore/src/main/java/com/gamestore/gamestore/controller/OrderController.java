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
import com.gamestore.gamestore.dto.UpdateOrderDTO;
import com.gamestore.gamestore.service.OrdersService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String,Object>> addOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<CreateCartDetailDTO> cartDetailDTOs){
        return ResponseEntity.ok().body(ordersService.addOrder(userDetails, cartDetailDTOs));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> updateOrder(@RequestBody UpdateOrderDTO updateOrderDTO){
        return ResponseEntity.ok().body((ordersService.updateOrder(updateOrderDTO)));
    }

    @DeleteMapping("/cancel/{orderID}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String,Object>> cancelEntity(@PathVariable Integer orderID){
        return ResponseEntity.ok().body(ordersService.cancelOrder(orderID));
    }

}
