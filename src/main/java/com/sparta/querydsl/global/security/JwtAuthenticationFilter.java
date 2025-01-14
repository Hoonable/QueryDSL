package com.sparta.querydsl.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.querydsl.domain.user.dto.LoginRequestDto;
import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.global.jwt.JwtUtil;
import com.sparta.querydsl.domain.user.repository.UserRepository;
import com.sparta.querydsl.global.enums.UserStatusEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository=userRepository;
        setFilterProcessesUrl("/users/login");
    }

    //로그인정보 추출, Spring Security의 인증 매니저에게 해당 정보를 전달하여 사용자를 인증
    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("start JwtAuthenticationFilter");
        try {
            // HttpServletRequest 를 LoginRequestDto 객체로 변환
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            // User 엔티티를 데이터베이스에서 가져옴
            User user = userRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            user.setStatus(UserStatusEnum.VERIFIED);////////테스트용

            // 사용자 상태를 확인
            if (user.getStatus() == UserStatusEnum.UNVERIFIED) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("인증이 필요합니다.");
                return null;
            } else if (user.getStatus() == UserStatusEnum.DELETED) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("탈퇴한 사용자입니다.");
                return null;
            }

            // 사용자 상태가 VERIFIED인 경우 인증 진행
            return getAuthenticationManager().authenticate( //사용자인증
                new UsernamePasswordAuthenticationToken( //사용자인증정보저장
                    requestDto.getUserId(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    //사용자인증에 성공했을때(로그인한 사용자가 db에 저장된 사용자일때)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("JwtAuthenticationFilter: Authentication successful!");
        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();//사용자 인증정보가져옴-getPrincipal

        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        //응답 헤더에 토큰을 담기
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.addHeader("Refresh-Token", refreshToken);

        // User의 refreshToken 필드에 Refresh Token 저장
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        response.setStatus(HttpServletResponse.SC_OK);

    }

    //사용자인증에 실패했을때
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException{
        log.info("JwtAuthenticationFilter: Authentication failed!");
        response.setStatus(401);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("msg", "로그인 실패");
        responseBody.put("statuscode", "401");

        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(responseBody);
        response.getWriter().write(responseJson);

    }
}
