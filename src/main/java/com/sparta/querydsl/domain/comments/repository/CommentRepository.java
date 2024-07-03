package com.sparta.querydsl.domain.comments.repository;

import com.sparta.querydsl.domain.comments.entity.Comment;
import com.sparta.querydsl.domain.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
