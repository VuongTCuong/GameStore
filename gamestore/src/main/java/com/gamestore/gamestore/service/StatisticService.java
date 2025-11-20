package com.gamestore.gamestore.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamestore.gamestore.repository.InvoiceRepository;
import com.gamestore.gamestore.repository.OrdersRepository;

@Service
public class StatisticService {

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private InvoiceRepository invoiceRepo;

    public Map<String,Object> statisticOrders(){
        Object[] statistic = ordersRepo.statisticOrders();

        Object[] firstRow = (Object[]) statistic[0];

        return Map.of(
            "totalOrders",firstRow[0],
            "totalRevenue",firstRow[1],
            "totalCustomers",firstRow[2]
        );
    }

    public Map<String,Object> statisticGames(){
        Object[] statistic = ordersRepo.statisticGames();

        Object[] firstRow = (Object[]) statistic[0];

        return Map.of(
            "gameName",firstRow[0],
            "quantitySold",firstRow[1],
            "totalRevenue",firstRow[2]
        );
    }

    public Map<String,Object> statisticInvoices(){
        Object[] statistic = invoiceRepo.statisticInvoices();

        
        return Map.of(
            "totalInvoice", statistic[0]
        );
    }

    public List<Map<String,Object>> firstThreeOrders(){
        Object[] statistic = ordersRepo.firstThreeOrders();

        List<Map<String,Object>> result = new ArrayList<>();
        for (Object o : statistic) {
            Object[] cur = (Object[]) o;
            Map<String,Object> temp = Map.of(
                "orderID", cur[0],
                "fullName", cur[1],
                "totalAmount", cur[2],
                "orderDate", cur[3]
            );
            result.add(temp);
        }
        return result;
    }

    public List<Map<String,Object>> soldOrders(String date1, String date2){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(date1, inputFormatter);
        LocalDate endDate = LocalDate.parse(date2, inputFormatter);

        Object[] statistic = ordersRepo.soldOrders(startDate,endDate);

        List<Map<String,Object>> result = new ArrayList<>();
        for (Object o : statistic) {
            Object[] cur = (Object[]) o;
            Map<String,Object> temp = Map.of(
                "orderID", cur[0],
                "fullName", cur[1],
                "orderDate", cur[2],
                "totalAmount", cur[3],
                "state",cur[4]
            );
            result.add(temp);
        }
        return result;
    }

    public List<Map<String,Object>> statisticbyMonth(String date1, String date2){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(date1, inputFormatter);
        LocalDate endDate = LocalDate.parse(date2, inputFormatter);

        Object[] statistic = ordersRepo.statisticbyMonth(startDate,endDate);

        List<Map<String,Object>> result = new ArrayList<>();
        for (Object o : statistic) {
            Object[] cur = (Object[]) o;
            Map<String,Object> temp = Map.of(
                "totalAmount", cur[0],
                "revenueMonth", cur[1]
            );
            result.add(temp);
        }
        return result;
    }

    public List<Map<String,Object>> statistictotalOrderbyUser(){
        Object[] statistic = ordersRepo.statistictotalOrderbyUser();

        List<Map<String,Object>> result = new ArrayList<>();
        for (Object o : statistic) {
            Object[] cur = (Object[]) o;
            Map<String,Object> temp = Map.of(
                "userID", cur[0],
                "fullName", cur[1],
                "email", cur[2],
                "totalOrders",cur[3]
            );
            result.add(temp);
        }
        return result;
    }
    public List<Map<String,Object>> statisticGameRevenue(String date1, String date2){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(date1, inputFormatter);
        LocalDate endDate = LocalDate.parse(date2, inputFormatter);

        Object[] statistic = ordersRepo.statisticGameRevenue(startDate,endDate);

        List<Map<String,Object>> result = new ArrayList<>();
        for (Object o : statistic) {
            Object[] cur = (Object[]) o;
            Map<String,Object> temp = Map.of(
                "gameName", cur[0],
                "totalQuantity", cur[1],
                "totalRevenue",cur[2]
            );
            result.add(temp);
        }
        return result;
    }

    public List<Map<String,Object>> statisticGameOrderDetailDesc(){
        Object[] statistic = ordersRepo.statisticGameOrderDetailDesc();

        List<Map<String,Object>> result = new ArrayList<>();
        for (Object o : statistic) {
            Object[] cur = (Object[]) o;
            Map<String,Object> temp = Map.of(
                "gameName", cur[0],
                "quantity", cur[1],
                "orderDate", cur[2]
            );
            result.add(temp);
        }
        return result;
    }
}
