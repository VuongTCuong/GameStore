package com.gamestore.gamestore.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamestore.gamestore.service.StatisticService;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;
    
    @GetMapping("/order")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> statisticOrders(){
        return ResponseEntity.ok().body(statisticService.statisticOrders());
    }

    @GetMapping("/game")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> statisticGames(){
        return ResponseEntity.ok().body(statisticService.statisticGames());
    }

    @GetMapping("/invoice")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> statisticInvoices(){
        return ResponseEntity.ok().body(statisticService.statisticInvoices());
    }

    @GetMapping("/first-three-order")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String,Object>>> firstThreeOrders(){
        return ResponseEntity.ok().body(statisticService.firstThreeOrders());
    }

    @GetMapping("/sold-order")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String,Object>>> soldOrders(@RequestParam String startDate, @RequestParam String endDate){
        return ResponseEntity.ok().body(statisticService.soldOrders(startDate,endDate));
    }

    @GetMapping("/statistic-Month")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String,Object>>> statisticbyMonth(@RequestParam String startDate, @RequestParam String endDate){
        return ResponseEntity.ok().body(statisticService.statisticbyMonth(startDate,endDate));
    }

    @GetMapping("/total-Order-User")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String,Object>>> statistictotalOrderbyUser(){
        return ResponseEntity.ok().body(statisticService.statistictotalOrderbyUser());
    }

    @GetMapping("/statistic-Game-Revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String,Object>>> statisticGameRevenue(@RequestParam String startDate, @RequestParam String endDate){
        return ResponseEntity.ok().body(statisticService.statisticGameRevenue(startDate,endDate));
    }

    @GetMapping("/order-detail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String,Object>>> statisticGameOrderDetailDesc(){
        return ResponseEntity.ok().body(statisticService.statisticGameOrderDetailDesc());
    }
}
