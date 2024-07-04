package com.sparta.querydsl.domain.like.repository;

import com.sparta.querydsl.domain.like.entity.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> , PostLikeRepositoryCustom  {

    Optional<PostLike> findByUserIdAndPostId(Long id, Long id1);
}
