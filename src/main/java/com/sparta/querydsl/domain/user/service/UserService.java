package com.sparta.querydsl.domain.user.service;

import com.sparta.querydsl.domain.user.dto.ProfileResponseDto;
import com.sparta.querydsl.global.config.ResponseCode;
import com.sparta.querydsl.domain.user.dto.SignupRequestDto;
import com.sparta.querydsl.domain.user.dto.UpdatePasswordRequestDto;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.domain.user.repository.UserRepository;
import com.sparta.querydsl.global.enums.UserStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @Transactional
    public ResponseCode signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();

        // 중복된 사용자 확인
        if (userRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException(ResponseCode.DUPLICATE_ENTITY.getMessage());
        }

        // 탈퇴한 사용자 확인
        if (userRepository.existsByUserIdAndStatus(userId, UserStatusEnum.DELETED)) {
            throw new IllegalArgumentException(ResponseCode.INVALID_INPUT_VALUE.getMessage());
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 사용자 생성 및 저장
        User user = new User();
        user.setUserId(userId);
        user.setPassword(encodedPassword); // 암호화된 비밀번호 저장
        user.setEmail(email);
        user.setStatus(UserStatusEnum.UNVERIFIED); // 초기 상태를 UNVERIFIED로 설정

        userRepository.save(user);

        return ResponseCode.CREATED;
    }

    @Transactional
    public ResponseCode updatePassword(UpdatePasswordRequestDto requestDto, String userId) {
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            return ResponseCode.UNAUTHORIZED;
        }

        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())) {
            return ResponseCode.INVALID_INPUT_VALUE;
        }

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);

        return ResponseCode.SUCCESS;
    }

    @Transactional
    public ProfileResponseDto getProfile(User myUser) {
        User user = userRepository.findById(myUser.getId()).orElseThrow();
        int postLikesCount = user.getPostLikes().size();
        int commentLikeCount = user.getCommentLikes().size();
        return new ProfileResponseDto(user, postLikesCount, commentLikeCount);
    }
}
