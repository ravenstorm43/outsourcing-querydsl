package com.sparta.outsourcing.board.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.board.entity.Board;
import com.sparta.outsourcing.like.entity.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sparta.outsourcing.board.entity.QBoard.board;
import static com.sparta.outsourcing.like.entity.QLike.like;

@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<Board> findAllByLikedUserIds(Long userId, Pageable pageable) {
        List<Board> boardList = jpaQueryFactory.selectFrom(board)
                .where(
                        board.id.in(jpaQueryFactory
                                .select(like.contentId).from(like)
                                .where(like.userId.eq(userId), like.contentType.eq(LikeType.BOARD))
                                .fetch())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdAt.desc())
                .fetch();

        long totalSize = jpaQueryFactory.select(Wildcard.countAsInt)
                .from(board)
                .where(
                        board.id.in(jpaQueryFactory
                                .select(like.contentId).from(like)
                                .where(like.userId.eq(userId), like.contentType.eq(LikeType.BOARD))
                                .fetch())
                )
                .fetch().get(0);
        return PageableExecutionUtils.getPage(boardList, pageable, () -> totalSize);
    }
}
