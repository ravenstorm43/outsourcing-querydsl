package com.sparta.outsourcing.board.repository;

import com.sparta.outsourcing.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    Page<Board> findAllByLikedUserIds(Long userId, Pageable pageable);
}
