package com.sparta.querydsl.domain.posts.dto;

import com.sparta.querydsl.domain.posts.entity.Post;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {
    private Long Id;
    private String userId;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifiedDate;

    public PostResponseDto(Post post) {

        this.Id = post.getId();
        this.userId = post.getUser().getUserId();
        this.content = post.getContent();
        this.postDate = post.getCreatedAt();
        this.modifiedDate = post.getModifiedAt();

    }

}
