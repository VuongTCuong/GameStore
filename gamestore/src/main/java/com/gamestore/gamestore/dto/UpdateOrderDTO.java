package com.gamestore.gamestore.dto;


import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDTO {

    @Min(value = 0, message = "Mã đơn hàng không hợp lệ")
    private Integer orderID;

    private LocalDate deliveryDate;
    
    @NotBlank(message = "Trạng thái đơn hàng không thể để trống")
    private String state;
}
