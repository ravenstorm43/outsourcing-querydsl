package com.sparta.outsourcing.comment.entity;

import com.sparta.outsourcing.board.entity.Board;
import com.sparta.outsourcing.comment.dto.CommentRequestDTO;
import com.sparta.outsourcing.common.Timestamped;
import com.sparta.outsourcing.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Board board, CommentRequestDTO commentRequestDTO, User user){
        this.board = board;
        this.comment = commentRequestDTO.getComment();
        this.user = user;
    }

}
