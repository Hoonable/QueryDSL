package com.sparta.querydsl.domain.like.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.querydsl.domain.comments.entity.Comment;
import com.sparta.querydsl.domain.comments.entity.QComment;
import com.sparta.querydsl.domain.like.entity.QCommentLike;
import com.sparta.querydsl.domain.like.entity.QPostLike;
import com.sparta.querydsl.domain.posts.entity.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements  CommentLikeRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> getCommentListWithePageAndSortCreatedAtDesc(long userId, long offset, int pageSize) {

        QCommentLike qCommentLike = QCommentLike.commentLike;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, qCommentLike.comment.createdAt);
        return jpaQueryFactory.from(qCommentLike)
            .select(qCommentLike.comment)
            .where(qCommentLike.user.id.eq(userId))
            .offset(offset)
            .limit(pageSize)
            .orderBy(orderSpecifier)
            .fetch();

    }
}
