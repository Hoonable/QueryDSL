package com.sparta.querydsl.domain.comments.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.querydsl.domain.like.entity.CommentLike;
import com.sparta.querydsl.domain.like.entity.PostLike;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.global.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
    private String content;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post; //N:1


    @Builder
    public Comment(User user, String content) {
        this.user = user;
        this.content = content;

    }

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();


    public void setContent(String content) {
        this.content = content;
    }
    public void setPost(Post post) {
        this.post = post;
    }

}
