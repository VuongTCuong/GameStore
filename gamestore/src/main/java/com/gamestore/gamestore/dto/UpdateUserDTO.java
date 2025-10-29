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
public class UpdateUserDTO {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max=30, message = "Họ tên không thể dài quá 30 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max=30, message = "Mật khẩu không thể dài quá 30 ký tự")
    private String password;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max=30, message = "Mật khẩu không thể dài quá 30 ký tự")
    private String confirmPassword;
}
