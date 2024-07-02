package com.sparta.outsourcing.comment.service;

import com.sparta.outsourcing.board.dto.BoardListResponseDto;
import com.sparta.outsourcing.board.entity.Board;
import com.sparta.outsourcing.board.repository.BoardRepository;
import com.sparta.outsourcing.comment.dto.CommentRequestDTO;
import com.sparta.outsourcing.comment.dto.CommentResponseDTO;
import com.sparta.outsourcing.comment.entity.Comment;
import com.sparta.outsourcing.comment.repository.CommentRepository;
import com.sparta.outsourcing.exception.ForbiddenException;
import com.sparta.outsourcing.exception.NotFoundException;
import com.sparta.outsourcing.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    //댓글 전체 조회
    public List<CommentResponseDTO> viewAllComment(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("선택한 게시물이 없습니다.")
        );

        Page<Comment> comments = commentRepository.findAllByBoardId(boardId, pageable);
        if (page > 0 && page >= comments.getTotalPages()) { //잘못된 페이지 요청 시 예외 처리
            throw new NotFoundException("해당 페이지를 찾을 수 없습니다.");
        }
        List<CommentResponseDTO> commentDataList = comments.stream().map(CommentResponseDTO::new).toList();
        if (commentDataList.isEmpty()) { //빈 페이지 메시지
            return new ArrayList<>();
        }
        return commentDataList;
    }
    public List<CommentResponseDTO> viewAllCommentByLikedUser(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findAllByLikedUserIds(user.getId(), pageable);
        if (page > 0 && page >= commentPage.getTotalPages()) { //잘못된 페이지 요청 시 예외 처리
            throw new NotFoundException("해당 페이지를 찾을 수 없습니다.");
        }
        List<CommentResponseDTO> commentDataList = commentPage.stream().map(CommentResponseDTO::new).toList();
        if (commentDataList.isEmpty()) { //빈 페이지 메시지
            return new ArrayList<>();
        }
        return commentDataList;
    }

    // 댓글 작성
    public CommentResponseDTO createComment(Long boardId, CommentRequestDTO commentRequestDTO, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("선택한 게시물이 없습니다.")
        );
        Comment comment = new Comment(board, commentRequestDTO, user);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDTO(saveComment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDTO updateComment(Long boardId,Long commentId, CommentRequestDTO commentRequestDTO, User user) {
        boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("선택한 게시물이 없습니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("조회한 댓글이 없습니다.")
        );

        if (Objects.equals(comment.getUser().getUserUid(), user.getUserUid())) {
            comment.updateComment(commentRequestDTO);
        } else {
            throw new NotFoundException("사용자 ID가 일치하지 않습니다.");
        }
        return new CommentResponseDTO(comment);
    }

    public void deleteComment(Long boardId, Long commentId, User user) {
        boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("선택한 게시물이 없습니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("조회한 댓글이 없습니다.")
        );

        if (Objects.equals(comment.getUser().getUserUid(), user.getUserUid())) {
            commentRepository.delete(comment);
        } else {
            throw new NotFoundException("사용자 ID가 일치하지 않습니다.");
        }
    }

    public void decreaseCommentLike(Long contentId) {
        Comment comment = commentRepository.findById(contentId).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        comment.decreaseLike();
    }

    public void increaseCommentLike(Long contentId) {
        Comment comment = commentRepository.findById(contentId).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        comment.increaseLike();
    }

    public void validateCommentLike(Long userId, Long contentId) {
        Comment comment = commentRepository.findById(contentId).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        if(comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("해당 댓글의 작성자입니다.");
        }
    }
}
