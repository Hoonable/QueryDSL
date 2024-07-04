package com.sparta.querydsl.domain.user.controller;

import com.sparta.querydsl.domain.user.dto.ProfileResponseDto;
import com.sparta.querydsl.global.config.ResponseCode;
import com.sparta.querydsl.global.HttpStatusResponseDto;
import com.sparta.querydsl.domain.user.dto.SignupRequestDto;
import com.sparta.querydsl.domain.user.dto.UpdatePasswordRequestDto;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.global.jwt.JwtUtil;
import com.sparta.querydsl.domain.user.repository.UserRepository;
import com.sparta.querydsl.global.security.UserDetailsImpl;
import com.sparta.querydsl.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository=userRepository;
    }


    // 공통 응답 처리 Generic 메서드
    private <T> ResponseEntity<HttpStatusResponseDto> createResponse(ResponseCode responseCode) {
        HttpStatusResponseDto response = new HttpStatusResponseDto(responseCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(responseCode.getStatusCode()));
    }

    private <T> ResponseEntity<T> createResponse(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatusResponseDto> signup(@Validated @RequestBody SignupRequestDto requestDto) {
        ResponseCode responseCode = userService.signup(requestDto);
        return createResponse(responseCode);
    }



    // 비밀번호 변경
    @PutMapping("/update-password")
    public ResponseEntity<HttpStatusResponseDto> updatePassword(
        @Validated @RequestBody UpdatePasswordRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userId = userDetails.getUser().getUserId();
        ResponseCode responseCode = userService.updatePassword(requestDto, userId);
        return createResponse(responseCode);
    }


    // 유효성 검사 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpStatusResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        HttpStatusResponseDto response = new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        return createResponse(response, HttpStatus.BAD_REQUEST);
    }

    // 일반 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpStatusResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseCode responseCode;
        if (ex.getMessage().equals(ResponseCode.DUPLICATE_ENTITY.getMessage())) {
            responseCode = ResponseCode.DUPLICATE_ENTITY;
        } else if (ex.getMessage().equals(ResponseCode.INVALID_INPUT_VALUE.getMessage())) {
            responseCode = ResponseCode.INVALID_INPUT_VALUE;
        } else {
            responseCode = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return createResponse(responseCode);
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpStatusResponseDto> handleException(Exception ex) {
        ex.printStackTrace();
        return createResponse(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // 헤더에서 토큰을 가져옴 ->
        String accessToken = jwtUtil.getAccessTokenFromHeader(request);
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(request);

        // 토큰이 유효한지 확인
        if (StringUtils.hasText(refreshToken) && jwtUtil.validateToken(refreshToken)) {
            Claims refreshTokenClaims = jwtUtil.getUserInfoFromToken(refreshToken);
            String userId = refreshTokenClaims.getSubject();

            // 유저의 리프레시 토큰 삭제
            User user = userRepository.findByUserId(userId).orElse(null);
            if (user != null) {
                user.setRefreshToken(null);
                userRepository.save(user);
            }
        }

        // 클라이언트 측 토큰 삭제 요청을 위해 응답 설정
        return createResponse("Logged out successfully", HttpStatus.OK);
    }



    @GetMapping("/profile")
    public ResponseEntity<HttpStatusResponseDto> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProfileResponseDto profile = userService.getProfile(userDetails.getUser());
        return  ResponseEntity.ok().body(new HttpStatusResponseDto(ResponseCode.SUCCESS, profile));

    }

}
