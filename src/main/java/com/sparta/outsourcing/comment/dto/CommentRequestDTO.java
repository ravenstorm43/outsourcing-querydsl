package com.sparta.outsourcing.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDTO {
    private String comment;
    private Long userId;
    private Long boardId;
}
