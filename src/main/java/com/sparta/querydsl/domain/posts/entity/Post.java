package com.sparta.querydsl.domain.posts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.global.Timestamped;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
    private String content;




    @Builder
    public Post(User user, String content) {
        this.user = user;
        this.content = content;

    }

    public void setContent(String content) {
        this.content = content;
    }

}
