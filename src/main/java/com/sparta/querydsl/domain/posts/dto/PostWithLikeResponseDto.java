package com.sparta.querydsl.domain.posts.dto;

import com.sparta.querydsl.domain.posts.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostWithLikeResponseDto {
    private Long Id;
    private String userId;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifiedDate;
    private int postLikeCount;

    public PostWithLikeResponseDto(Post post, int cnt) {
        this.Id = post.getId();
        this.userId = post.getUser().getUserId();
        this.content = post.getContent();
        this.postDate = post.getCreatedAt();
        this.modifiedDate = post.getModifiedAt();
        this.postLikeCount = cnt;
    }

}
