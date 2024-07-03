package com.sparta.querydsl.domain.comments.dto;

import com.sparta.querydsl.domain.comments.entity.Comment;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long Id;
    private String userId;
    private Long postId;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifiedDate;
    public CommentResponseDto(Comment comment) {
        this.Id = comment.getId();
        this.userId = comment.getUser().getUserId();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.postDate = comment.getCreatedAt();
        this.modifiedDate = comment.getModifiedAt();

    }
}
