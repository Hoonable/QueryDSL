package com.sparta.querydsl.domain.comments.dto;

import com.sparta.querydsl.domain.comments.entity.Comment;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;

    public Comment toEntity(User user) {
        return Comment.builder()
            .user(user)
            .content(content)
            .build();
    }
}
