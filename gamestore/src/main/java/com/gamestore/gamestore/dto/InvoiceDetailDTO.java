package com.gamestore.gamestore.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailDTO {
    
    private String fullName;
    private String email;
    private Integer invoiceID; 
    private LocalDate invoiceDate;
    private String paymentStatus;
}
