package com.sparta.querydsl.domain.comments.controller;

import com.sparta.querydsl.domain.comments.dto.CommentRequestDto;
import com.sparta.querydsl.domain.comments.dto.CommentResponseDto;
import com.sparta.querydsl.domain.comments.entity.Comment;
import com.sparta.querydsl.domain.comments.service.CommentService;
import com.sparta.querydsl.domain.posts.dto.PostRequestDto;
import com.sparta.querydsl.domain.posts.dto.PostResponseDto;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.posts.service.PostService;
import com.sparta.querydsl.global.HttpStatusResponseDto;
import com.sparta.querydsl.global.config.ResponseCode;
import com.sparta.querydsl.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService commentService;

    @PostMapping
    public HttpStatusResponseDto createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto dto, @PathVariable Long postId) {
        try {
            Comment comment = commentService.createComment(dto, userDetails.getUser(), postId);
            return new HttpStatusResponseDto(ResponseCode.CREATED, new CommentResponseDto(comment));
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }
    }

    @DeleteMapping("{commentId}")
    public HttpStatusResponseDto deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }
    }

    @PutMapping("{commentId}")
    public HttpStatusResponseDto updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto dto ,@PathVariable Long commentId) {
        try {
            commentService.updateComment(commentId, dto, userDetails.getUser());
            return new HttpStatusResponseDto(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }
    }

    @GetMapping("{page}")
    public HttpStatusResponseDto getComments(@PathVariable int page) {
        try {
            List<CommentResponseDto> comments = commentService.getComments(page-1);
            return new HttpStatusResponseDto(ResponseCode.SUCCESS, comments);
        }catch (Exception e) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }
    }
}
