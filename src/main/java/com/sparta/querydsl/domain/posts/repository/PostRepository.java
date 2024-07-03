package com.sparta.querydsl.domain.posts.repository;


import com.sparta.querydsl.domain.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
