package com.sparta.outsourcing.user.dto;

import com.sparta.outsourcing.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String userUid;
    private String username;
    private String intro;
    private Long boardLikeCount;
    private Long commentLikeCount;

    public UserResponseDto(User user) {
        this.userUid = user.getUserUid();
        this.username = user.getUsername();
        this.intro = user.getIntro();
    }
    public UserResponseDto(User user, Long boardLikeCount, Long commentLikeCount) {
        this.userUid = user.getUserUid();
        this.username = user.getUsername();
        this.intro = user.getIntro();
        this.boardLikeCount = boardLikeCount;
        this.commentLikeCount = commentLikeCount;
    }
}
