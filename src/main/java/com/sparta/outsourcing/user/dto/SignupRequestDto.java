package com.sparta.outsourcing.user.dto;

import com.sparta.outsourcing.user.entity.UserStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignupRequestDto {
    private String userUid;
    private String password;
    private String username;
    private String intro;
    private UserStatus role;
}