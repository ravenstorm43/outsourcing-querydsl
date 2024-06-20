package com.sparta.outsourcing.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateRequest {
    private String title;
    private String content;
}
