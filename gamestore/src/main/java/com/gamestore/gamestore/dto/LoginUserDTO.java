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
public class LoginUserDTO {

    @NotBlank(message = "Tài khoản không thể để trống")
    private String account;

    @NotBlank(message = "Mật khẩu không thể đề trống")
    private String password;
}
