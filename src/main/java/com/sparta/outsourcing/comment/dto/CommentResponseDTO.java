package com.sparta.outsourcing.comment.dto;

import com.sparta.outsourcing.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDTO {
    private String comment;

    public CommentResponseDTO(Comment comment) {
        this.comment = comment.getComment();
    }
}
