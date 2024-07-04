package com.sparta.querydsl.domain.posts.service;


import com.sparta.querydsl.domain.posts.dto.PostRequestDto;
import com.sparta.querydsl.domain.posts.dto.PostResponseDto;
import com.sparta.querydsl.domain.posts.dto.PostWithLikeResponseDto;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.posts.repository.PostRepository;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.global.exception.InvalidPageException;
import com.sparta.querydsl.global.exception.InvalidUserException;
import com.sparta.querydsl.global.exception.PostNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(PostRequestDto dto, User user) {

        var newPost = dto.toEntity(user);
        return postRepository.save(newPost);
    }

    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        if (post.getUser().getId().equals(user.getId())) {
            postRepository.delete(post);
        } else {
            throw new InvalidUserException("작성자가 아닙니다.");
        }
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto dto, User user) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        if (post.getUser().getId().equals(user.getId())) {
            post.setContent(dto.getContent());
        } else {
            throw new InvalidUserException("작성자가 아닙니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<PostWithLikeResponseDto> getPosts(int page) {
        if (page < 0) {
            throw new InvalidPageException("잘못된 페이지입니다.");
        }
        Sort sort = Sort.by(Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        if (posts.isEmpty()) {
            throw new InvalidPageException("잘못된 페이지입니다.");
        }
        return (posts.map(post -> new PostWithLikeResponseDto(post, post.getPostLikes().size()))
            .getContent());
    }
}
