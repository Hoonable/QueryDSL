package com.sparta.querydsl.domain.posts.controller;


import com.sparta.querydsl.domain.posts.dto.PostRequestDto;
import com.sparta.querydsl.domain.posts.dto.PostResponseDto;
import com.sparta.querydsl.domain.posts.dto.PostWithLikeResponseDto;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.posts.service.PostService;
import com.sparta.querydsl.global.HttpStatusResponseDto;
import com.sparta.querydsl.global.config.ResponseCode;
import com.sparta.querydsl.global.security.UserDetailsImpl;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/posts")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;

    @PostMapping
    public HttpStatusResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody PostRequestDto dto) {
        Post post = postService.createPost(dto, userDetails.getUser());
        return new HttpStatusResponseDto(ResponseCode.CREATED, new PostResponseDto(post));
    }

    @DeleteMapping("{postId}")
    public HttpStatusResponseDto deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long postId) {
        try {
            postService.deletePost(postId, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }
    }

    @PutMapping("{postId}")
    public HttpStatusResponseDto updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody PostRequestDto dto ,@PathVariable Long postId) {
        try {
            postService.updatePost(postId, dto, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }
    }

    @GetMapping("{page}")
    public HttpStatusResponseDto getPosts(@PathVariable int page) {
        try {
            List<PostWithLikeResponseDto> posts = postService.getPosts(page-1);
            return new HttpStatusResponseDto(ResponseCode.SUCCESS, posts);
        }catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }
    }
}
