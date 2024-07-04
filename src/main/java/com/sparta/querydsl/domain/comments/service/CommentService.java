package com.sparta.querydsl.domain.comments.service;

import com.sparta.querydsl.domain.comments.dto.CommentRequestDto;
import com.sparta.querydsl.domain.comments.dto.CommentResponseDto;
import com.sparta.querydsl.domain.comments.dto.CommentWithLikeResponseDto;
import com.sparta.querydsl.domain.comments.entity.Comment;
import com.sparta.querydsl.domain.comments.repository.CommentRepository;
import com.sparta.querydsl.domain.posts.dto.PostResponseDto;
import com.sparta.querydsl.domain.posts.dto.PostWithLikeResponseDto;
import com.sparta.querydsl.domain.posts.entity.Post;
import com.sparta.querydsl.domain.posts.repository.PostRepository;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.global.exception.CommentNotFoundException;
import com.sparta.querydsl.global.exception.InvalidPageException;
import com.sparta.querydsl.global.exception.InvalidUserException;
import com.sparta.querydsl.global.exception.PostNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public Comment createComment(CommentRequestDto dto, User user, Long postId) {
        var newComment = dto.toEntity(user);
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("해당 게시물이 존재하지 않습니다."));
        newComment.setPost(post);
        return commentRepository.save(newComment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        if (postRepository.findById(comment.getPost().getId()).isEmpty()) {
            throw new PostNotFoundException("해당 게시물이 존재하지 않습니다.");
        }

        if(comment.getUser().getId().equals(user.getId())) {
            commentRepository.delete(comment);
        }
        else{
            throw new InvalidUserException("작성자가 아닙니다.");
        }
    }

    @Transactional
    public void updateComment(Long commentId, CommentRequestDto dto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        if (postRepository.findById(comment.getPost().getId()).isEmpty()) {
            throw new PostNotFoundException("해당 게시물이 존재하지 않습니다.");
        }

        if(comment.getUser().getId().equals(user.getId())) {
            comment.setContent(dto.getContent());
        }
        else{
            throw new InvalidUserException("작성자가 아닙니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<CommentWithLikeResponseDto> getComments(int page) {
        if(page<0){
            throw new InvalidPageException("잘못된 페이지입니다.");
        }
        Sort sort = Sort.by(Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<Comment> comments = commentRepository.findAll(pageable);

        if (comments.isEmpty()){
            throw new InvalidPageException("잘못된 페이지입니다.");
        }

        return (comments.map(comment -> new CommentWithLikeResponseDto(comment, comment.getCommentLikes().size()))
            .getContent());
    }
}
