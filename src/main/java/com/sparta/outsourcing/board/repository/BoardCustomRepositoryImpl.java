package com.sparta.outsourcing.board.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.board.entity.Board;
import com.sparta.outsourcing.board.entity.QBoard;
import com.sparta.outsourcing.comment.entity.OrderByEnum;
import com.sparta.outsourcing.common.QueryDslUtil;
import com.sparta.outsourcing.like.entity.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.outsourcing.board.entity.QBoard.board;
import static com.sparta.outsourcing.like.entity.QLike.like;
import static com.sparta.outsourcing.follow.entity.QFollow.follow;
import static com.sparta.outsourcing.user.entity.QUser.user;
import static org.springframework.util.ObjectUtils.isEmpty;

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

    @Override
    public Page<Board> getAllBoardsByFollowedUsers(Long userId, Pageable pageable) {
        List<OrderSpecifier> ORDERS = getOrderSpecifier(pageable);
        List<Board> boardList = jpaQueryFactory.selectFrom(board)
                .where(
                        board.user.id.in(
                                jpaQueryFactory.select(follow.followedUser.id)
                                        .from(follow)
                                        .where(follow.user.id.eq(userId))
                                        .fetch())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .fetch();
        long totalSize = jpaQueryFactory.select(Wildcard.count)
                .from(board)
                .where(
                        board.user.id.in(
                                jpaQueryFactory.select(follow.followedUser.id)
                                        .from(follow)
                                        .where(follow.user.id.eq(userId))
                                        .fetch())
                )
                .fetch().get(0);
        return PageableExecutionUtils.getPage(boardList, pageable, () -> totalSize);
    }

    private List<OrderSpecifier> getOrderSpecifier(Pageable pageable) {
        List<OrderSpecifier> orders = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "createdAt":
                        OrderSpecifier<?> orderCreatedAt = QueryDslUtil.getSortedColumn(direction, board, "createdAt");
                        orders.add(orderCreatedAt);
                        break;
                    case "user":
                        OrderSpecifier<?> orderUser = QueryDslUtil.getSortedColumn(direction, board, "generatedname");
                        orders.add(orderUser);
                        break;
                }
            }
        }
        return orders;
    }
}
