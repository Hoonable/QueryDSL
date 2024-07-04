package com.sparta.querydsl.domain.like.service;


import com.sparta.querydsl.domain.like.entity.PostLike;
import com.sparta.querydsl.domain.like.repository.PostLikeRepository;
import com.sparta.querydsl.domain.posts.dto.PostWithLikeResponseDto;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.posts.repository.PostRepository;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.domain.user.repository.UserRepository;
import com.sparta.querydsl.global.exception.InvalidPageException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PostWithLikeResponseDto> getPostsILike(int page, User user) {


        if (page < 0) {
            throw new InvalidPageException("잘못된 페이지입니다.");
        }
        PageRequest pageRequest = PageRequest.of(page, 5);
        List<Post> posts = postLikeRepository.getPostListWithePageAndSortCreatedAtDesc( user.getId() , pageRequest.getOffset(), pageRequest.getPageSize());

        if (posts.isEmpty()) {
            throw new InvalidPageException("잘못된 페이지입니다.");
        }
        return posts.stream().map(post -> new PostWithLikeResponseDto(post, post.getPostLikes().size())).toList();

    }


}
