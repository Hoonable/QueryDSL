package com.sparta.querydsl.domain.like.repository;

import com.sparta.querydsl.domain.comments.entity.Comment;
import com.sparta.querydsl.domain.posts.entity.Post;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepositoryCustom {

    List<Comment>  getCommentListWithePageAndSortCreatedAtDesc(long userId, long offset, int pageSize);

}
