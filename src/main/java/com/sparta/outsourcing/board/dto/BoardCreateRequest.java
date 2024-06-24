package com.sparta.outsourcing.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardCreateRequest {
    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;
    private String content;
}
