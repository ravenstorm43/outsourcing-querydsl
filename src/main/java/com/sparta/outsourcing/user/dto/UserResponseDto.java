package com.sparta.outsourcing.user.dto;

import com.sparta.outsourcing.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String userUid;
    private String username;
    private String intro;

    public UserResponseDto(User user) {
        this.userUid = user.getUserUid();
        this.username = user.getUsername();
        this.intro = user.getIntro();
    }
}
