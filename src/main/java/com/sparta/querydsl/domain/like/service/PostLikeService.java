package com.sparta.querydsl.domain.like.service;

import com.sparta.querydsl.domain.like.entity.PostLike;
import com.sparta.querydsl.domain.like.repository.PostLikeRepository;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.posts.repository.PostRepository;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.domain.user.repository.UserRepository;
import com.sparta.querydsl.global.exception.InvalidUserException;
import com.sparta.querydsl.global.exception.LikeNotFoundException;
import com.sparta.querydsl.global.exception.PostNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void doLike(Long postId, User liekUser) {

        User user = userRepository.findById(liekUser.getId()).orElseThrow();//lazy,transactional

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("해당 게시물이 존재하지 않습니다."));
        if (post.getId().equals(user.getId())) {
            throw new InvalidUserException("자신의 게시물에 좋아요할 수 없습니다.");
        }
        List<PostLike> postLikes = user.getPostLikes();
        if (postLikes.stream()  // 좋아요는 한번만 가능
            .noneMatch(postLike -> postLike.getPost().getId().equals(post.getId()))) {
            postLikeRepository.save(PostLike.builder()
                .post(post)
                .user(user)
                .build());
        }
    }

    public void undoLike(Long postId, User likeUser) {
        User user = userRepository.findById(likeUser.getId()).orElseThrow();
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("해당 게시물이 존재하지 않습니다."));

        PostLike postLike = postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId()).orElseThrow(()->new LikeNotFoundException("해당 좋아요가 존재하지 않습니다."));
        postLikeRepository.delete(postLike);
    }
}
