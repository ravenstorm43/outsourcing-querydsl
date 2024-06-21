package com.sparta.outsourcing.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDTO {

    @NotBlank(message = "공백이거나 null인 것은 불가합니다.")
    private String comment;
}
