package com.gamestore.gamestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartDetailDTO {

    private Integer gameID;

    private float priceAtPurchase;
    private Integer quantity;
}
