package com.sparta.querydsl.domain.user.entity;


import com.sparta.querydsl.global.Timestamped;
import com.sparta.querydsl.global.enums.UserStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User extends Timestamped {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 필드
    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String introduction;

    @Column
    private String pictureURL;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatusEnum status;

    @Column
    private String refreshToken;

    public User(String userId, String password, String email, String name, UserStatusEnum status) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.status = status;
    }

    public User(String userId, String password, String email, UserStatusEnum status) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.status = status;
    }


}