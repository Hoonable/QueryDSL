package com.sparta.querydsl.domain.like.repository;

import com.sparta.querydsl.domain.posts.entity.Post;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepositoryCustom {

    List<Post> getPostListWithePageAndSortCreatedAtDesc(long userId, long offset, int pageSize);

}
