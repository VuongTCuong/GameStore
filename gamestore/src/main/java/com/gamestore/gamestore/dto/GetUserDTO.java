package com.gamestore.gamestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDTO {
    private Integer userID;
    private String fullName;
    private String account;
    private String email;
    private String role;
    private String state;
}
