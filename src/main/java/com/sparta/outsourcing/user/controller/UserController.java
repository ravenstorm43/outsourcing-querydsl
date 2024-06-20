package com.sparta.outsourcing.user.controller;

import com.sparta.outsourcing.user.dto.CommonResponse;
import com.sparta.outsourcing.user.dto.SignupRequestDto;
import com.sparta.outsourcing.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<CommonResponse<Void>> signup(@RequestBody SignupRequestDto requestDto) {
        CommonResponse<Void> response = new CommonResponse<>("회원가입 성공", 200);
        userService.signup(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}