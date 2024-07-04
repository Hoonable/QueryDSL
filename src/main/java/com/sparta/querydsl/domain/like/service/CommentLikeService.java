package com.sparta.querydsl.domain.like.service;

import com.sparta.querydsl.domain.comments.entity.Comment;
import com.sparta.querydsl.domain.comments.repository.CommentRepository;
import com.sparta.querydsl.domain.like.entity.CommentLike;
import com.sparta.querydsl.domain.like.entity.PostLike;
import com.sparta.querydsl.domain.like.repository.CommentLikeRepository;
import com.sparta.querydsl.domain.like.repository.PostLikeRepository;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.posts.repository.PostRepository;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.domain.user.repository.UserRepository;
import com.sparta.querydsl.global.exception.CommentNotFoundException;
import com.sparta.querydsl.global.exception.InvalidUserException;
import com.sparta.querydsl.global.exception.LikeNotFoundException;
import com.sparta.querydsl.global.exception.PostNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void doLike(Long postId, User liekUser) {

        User user = userRepository.findById(liekUser.getId()).orElseThrow();//lazy,transactional

        Comment comment = commentRepository.findById(postId)
            .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        if (comment.getId().equals(user.getId())) {
            throw new InvalidUserException("자신의 댓글에 좋아요할 수 없습니다.");
        }
        List<CommentLike> commentLikes = user.getCommentLikes();
        if (commentLikes.stream()  // 좋아요는 한번만 가능
            .noneMatch(postLike -> postLike.getComment().getId().equals(comment.getId()))) {
            commentLikeRepository.save(CommentLike.builder()
                .comment(comment)
                .user(user)
                .build());
        }
    }

    public void undoLike(Long postId, User likeUser) {
        User user = userRepository.findById(likeUser.getId()).orElseThrow();
        Comment comment= commentRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("해당 게시물이 존재하지 않습니다."));

        CommentLike commentLike = commentLikeRepository.findByUserIdAndCommentId(user.getId(), comment.getId())
            .orElseThrow(() -> new LikeNotFoundException("해당 좋아요가 존재하지 않습니다."));
        commentLikeRepository.delete(commentLike);
    }
}
