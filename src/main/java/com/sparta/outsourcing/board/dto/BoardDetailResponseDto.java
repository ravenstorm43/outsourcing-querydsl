package com.sparta.outsourcing.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardDetailResponseDto {
    private int httpStatusCode;
    private String message;
    private BoardData data;

    @Getter
    @AllArgsConstructor
    public static class BoardData {
        private String title;
        private String content;
        private String username;
        private LocalDateTime updatedAt;
    }
}
