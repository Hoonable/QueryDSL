package com.sparta.querydsl.domain.like.repository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.querydsl.domain.like.entity.QPostLike;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.posts.entity.QPost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostListWithePageAndSortCreatedAtDesc(long userId, long offset, int pageSize) {

        QPostLike qPostLike = QPostLike.postLike;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, qPostLike.post.createdAt);
        return jpaQueryFactory.from(qPostLike)
            .select(qPostLike.post)
            .where(qPostLike.user.id.eq(userId))
            .offset(offset)
            .limit(pageSize)
            .orderBy(orderSpecifier)
            .fetch();

    }
}
