package com.sparta.querydsl.domain.like.controller;

import com.sparta.querydsl.domain.comments.entity.Comment;
import com.sparta.querydsl.domain.like.service.CommentLikeService;
import com.sparta.querydsl.domain.like.service.PostLikeService;
import com.sparta.querydsl.global.HttpStatusResponseDto;
import com.sparta.querydsl.global.config.ResponseCode;
import com.sparta.querydsl.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/users/posts/{postId}/comments/{commentId}/like")
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PostMapping
    public HttpStatusResponseDto doLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentLikeService.doLike(commentId, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }
    }

    @DeleteMapping
    public HttpStatusResponseDto undoLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        try {
            commentLikeService.undoLike(commentId, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }

    }


}