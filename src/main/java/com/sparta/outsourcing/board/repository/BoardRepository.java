package com.sparta.outsourcing.board.repository;

import com.sparta.outsourcing.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //최신순으로 게시물 페이징
    Page<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
