package com.gamestore.gamestore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    
    @NotBlank(message = "Họ tên không được để trống")
    @Size(max=30, message = "Họ tên không thể dài quá 30 ký tự")
    private String fullName;

    @NotBlank(message = "Tài khoản không được để trống")
    @Size(max=20, message = "Tài khoản không thể dài quá 20 ký tự")
    private String account;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max=30, message = "Mật khẩu không thể dài quá 30 ký tự")
    private String password;

    @NotBlank(message = "Email không được để trống")
    private String email;
}
