package com.gamestore.gamestore.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.gamestore.gamestore.entity.Invoice;
import com.gamestore.gamestore.entity.OrderDetail;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.InvoiceRepository;
import com.gamestore.gamestore.repository.OrderDetailRepository;


@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private OrderDetailRepository orderDetailRepo;


    // khi người dùng thanh toán
    public Map<String,Object> updateInvoice(Integer orderID){
        Invoice invoice = invoiceRepo.findByOrderID(orderID).orElseThrow(()-> new MainErrorException("Không tìm thấy hoá đơn"));
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setPaymentStatus("Đã thanh toán");
        invoiceRepo.save(invoice);

        return Map.of(
            "status","thành công",
            "message","thanh toán thành công"
        );
    }

    public Map<String,Object> getInvoiceDetail(Integer orderID){
        Object[] invoiceDetail = invoiceRepo.detailfindByOrderID(orderID);
        List<OrderDetail> orderDetail = orderDetailRepo.findByOrderID(orderID);

        Object[] firstRow = (Object[]) invoiceDetail[0];
        
        System.out.println(Arrays.toString(invoiceDetail));
        return Map.of(
            "fullName", firstRow[0],
            "email", firstRow[1],
            "invoiceID", firstRow[2],
            "invoiceDate", firstRow[3],
            "paymentStatus", firstRow[4],
            "orderDetail", orderDetail
        );
    }
}
