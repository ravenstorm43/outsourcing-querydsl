package com.sparta.outsourcing.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePasswordDto {
    public String oldpassword;
    public String newpassword;
}
