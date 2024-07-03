package com.sparta.querydsl.domain.posts.dto;

import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    private String content;

    public Post toEntity(User user) {
        return Post.builder()
            .user(user)
            .content(content)
            .build();

    }
}
