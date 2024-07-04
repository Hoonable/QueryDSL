package com.sparta.querydsl.domain.like.controller;


import com.sparta.querydsl.domain.comments.dto.CommentWithLikeResponseDto;
import com.sparta.querydsl.domain.like.service.UserLikeService;
import com.sparta.querydsl.domain.posts.dto.PostWithLikeResponseDto;
import com.sparta.querydsl.global.HttpStatusResponseDto;
import com.sparta.querydsl.global.config.ResponseCode;
import com.sparta.querydsl.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/like")
@RequiredArgsConstructor
public class UserLikeController {

    private final UserLikeService userLikeService;

    @GetMapping("posts/{page}")
    public HttpStatusResponseDto getPostsILike(@PathVariable int page,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<PostWithLikeResponseDto> posts = userLikeService.getPostsILike(page - 1, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS, posts);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }
    }

    @GetMapping("comments/{page}")
    public HttpStatusResponseDto getCommentsILike(@PathVariable int page,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<CommentWithLikeResponseDto> comments = userLikeService.getCommentsILike(page - 1, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS, comments);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }
    }





}
