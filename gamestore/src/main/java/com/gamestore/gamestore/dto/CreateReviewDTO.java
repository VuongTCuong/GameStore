package com.gamestore.gamestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO {
    @NotBlank(message = "nội dung review không được để trống")
    private String reviewContent;
}
