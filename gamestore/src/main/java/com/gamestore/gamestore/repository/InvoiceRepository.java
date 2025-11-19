package com.gamestore.gamestore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;



import com.gamestore.gamestore.entity.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {



    Optional<Invoice> findByOrderID(Integer orderID);

    @NativeQuery(value = "select fullName, email, invoiceID, invoiceDate, paymentStatus "+ 
                 "from orders, user, invoice "+
                 "where invoice.orderID = ?1 and invoice.orderID=orders.orderID and orders.userID=user.userID")              
    Object[] detailfindByOrderID(Integer orderID);

    @NativeQuery("select count(*) as totalInvoice "+
                 "from invoice")
    Object[] statisticInvoices();

}
