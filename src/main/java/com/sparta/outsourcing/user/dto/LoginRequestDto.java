package com.sparta.outsourcing.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String userUid;
    private String password;
}
