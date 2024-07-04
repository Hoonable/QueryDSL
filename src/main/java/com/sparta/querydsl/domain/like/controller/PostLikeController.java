package com.sparta.querydsl.domain.like.controller;

import com.sparta.querydsl.domain.like.service.PostLikeService;
import com.sparta.querydsl.global.HttpStatusResponseDto;
import com.sparta.querydsl.global.config.ResponseCode;
import com.sparta.querydsl.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/posts/{postId}/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public HttpStatusResponseDto doLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            postLikeService.doLike(postId, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }
    }

    @DeleteMapping
    public HttpStatusResponseDto undoLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        try {
            postLikeService.undoLike(postId, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }

    }


}
