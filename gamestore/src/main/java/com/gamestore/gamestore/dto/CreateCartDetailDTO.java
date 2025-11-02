package com.gamestore.gamestore.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartDetailDTO {

    @Min(value = 1, message = "Mã trò chơi không hợp lệ")
    private Integer gameID;

    private float priceAtPurchase;
    private Integer quantity;
}
