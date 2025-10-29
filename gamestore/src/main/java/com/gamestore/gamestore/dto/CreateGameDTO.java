package com.gamestore.gamestore.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameDTO {

    @NotBlank(message = "Tên trò chơi không được để trống")
    @Size(max=50, message = "Tên trò chơi không thể dài quá 50 ký tự")
    private String gameName;

    private String poster;

    private String genre;
    private String gameDescription;
    private String platform;
    private String publisher;

    @NotNull(message =  "Ngày phát hành không được để trống")
    private LocalDate releaseDate;

    @Min(value = 0)
    private Integer stockQuantity;

    @Min(value = 0)
    private float price;
}
