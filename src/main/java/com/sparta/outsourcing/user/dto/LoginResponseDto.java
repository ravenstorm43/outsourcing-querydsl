package com.sparta.outsourcing.user.dto;

import com.sparta.outsourcing.user.entity.UserStatus;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String userUid;
    private String username;
    private UserStatus role;

    public LoginResponseDto(String userUid, String username, UserStatus role) {
        this.userUid = userUid;
        this.username = username;
        this.role = role;
    }
}
