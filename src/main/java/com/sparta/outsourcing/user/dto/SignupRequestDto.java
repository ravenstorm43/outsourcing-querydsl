package com.sparta.outsourcing.user.dto;

import com.sparta.outsourcing.user.entity.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "아이디는 4자 이상 10자 이하의 영문(대문자 제외) + 숫자만을 허용합니다.")
    private String userUid;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[`~!@#$%^])[A-Za-z\\d`~!@#$%^]{8,15}$",
            message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해서 8자 이상 15자여야 합니다.")
    private String password;
    @NotBlank
    private String username;
    private String intro;
    private UserStatus role;
}