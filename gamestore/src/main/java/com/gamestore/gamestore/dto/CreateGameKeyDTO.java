package com.gamestore.gamestore.dto;


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
public class CreateGameKeyDTO {

    @NotBlank(message = "Key trò chơi không được để trống")
    @Size(max = 255, message = "Key trò chơi không được vượt quá 255 ký tự")
    private String keyValue;


    private String status;

    @Min(value = 0, message = "Mã trò chơi không hợp lệ")
    @NotNull(message = "Mã trò chơi không được để trống")
    private Integer gameID;
}
