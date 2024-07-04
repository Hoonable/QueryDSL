package com.sparta.querydsl.domain.comments.dto;

import com.sparta.querydsl.domain.comments.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWithLikeResponseDto {
    private Long Id;
    private String userId;
    private Long postId;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifiedDate;
    private int commentLikeCount;

    public CommentWithLikeResponseDto(Comment comment, int cnt) {
        this.Id = comment.getId();
        this.userId = comment.getUser().getUserId();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.postDate = comment.getCreatedAt();
        this.modifiedDate = comment.getModifiedAt();
        this.commentLikeCount = cnt;

    }
}
