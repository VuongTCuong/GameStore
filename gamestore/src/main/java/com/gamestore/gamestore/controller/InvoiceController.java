package com.gamestore.gamestore.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamestore.gamestore.service.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    @PutMapping("/update/{orderID}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String,Object>> updateInvoice(@PathVariable Integer orderID){
        return ResponseEntity.ok().body(invoiceService.updateInvoice(orderID));
    }

    @GetMapping("/get/{orderID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> getInvoiceDetail(@PathVariable Integer orderID){
        return ResponseEntity.ok().body(invoiceService.getInvoiceDetail(orderID));
    }
}
