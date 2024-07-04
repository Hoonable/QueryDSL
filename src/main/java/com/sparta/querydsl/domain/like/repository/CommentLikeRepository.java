package com.sparta.querydsl.domain.like.repository;

import com.sparta.querydsl.domain.like.entity.CommentLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>, CommentLikeRepositoryCustom {

    Optional<CommentLike> findByUserIdAndCommentId(Long id, Long id1);
}
