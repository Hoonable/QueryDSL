package com.sparta.querydsl.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 2093391946L;

    public static final QUser user = new QUser("user");

    public final com.sparta.querydsl.global.QTimestamped _super = new com.sparta.querydsl.global.QTimestamped(this);

    public final ListPath<com.sparta.querydsl.domain.like.entity.CommentLike, com.sparta.querydsl.domain.like.entity.QCommentLike> commentLikes = this.<com.sparta.querydsl.domain.like.entity.CommentLike, com.sparta.querydsl.domain.like.entity.QCommentLike>createList("commentLikes", com.sparta.querydsl.domain.like.entity.CommentLike.class, com.sparta.querydsl.domain.like.entity.QCommentLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath pictureURL = createString("pictureURL");

    public final ListPath<com.sparta.querydsl.domain.like.entity.PostLike, com.sparta.querydsl.domain.like.entity.QPostLike> postLikes = this.<com.sparta.querydsl.domain.like.entity.PostLike, com.sparta.querydsl.domain.like.entity.QPostLike>createList("postLikes", com.sparta.querydsl.domain.like.entity.PostLike.class, com.sparta.querydsl.domain.like.entity.QPostLike.class, PathInits.DIRECT2);

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<com.sparta.querydsl.global.enums.UserStatusEnum> status = createEnum("status", com.sparta.querydsl.global.enums.UserStatusEnum.class);

    public final StringPath userId = createString("userId");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

