package com.sparta.querydsl.domain.user.dto;

import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {

    private String userId;
    private String name;
    private String email;
    private int postLikesCnt;
    private int commentLikesCnt;


    public ProfileResponseDto(User user, int postLikesCnt, int commentLikesCnt) {

        this.name = user.getName();
        this.email = user.getEmail();
        this.userId = user.getUserId();
        this.postLikesCnt = postLikesCnt;
        this.commentLikesCnt = commentLikesCnt;


    }
}
