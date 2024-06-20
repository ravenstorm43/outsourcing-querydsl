package com.sparta.outsourcing.comment.service;

import com.sparta.outsourcing.comment.dto.CommentResponseDTO;
import com.sparta.outsourcing.comment.entity.Comment;
import com.sparta.outsourcing.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    public List<CommentResponseDTO> viewAllComment(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 없습니다.")
        );

        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return comments.stream().map(CommentResponseDTO::new).toList();
    }
}
