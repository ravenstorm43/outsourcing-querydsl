package com.sparta.outsourcing.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class BoardListResponseDto {
    private int httpStatusCode;
    private String message;
    private List<BoardData> data;

    @Getter
    @AllArgsConstructor
    public static class BoardData {
        private Long id;
        private String title;
        private String username;
        private LocalDateTime updatedAt;
    }
}
