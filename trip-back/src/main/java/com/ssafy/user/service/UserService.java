package com.ssafy.user.service;

import com.ssafy.user.dto.UserJoinRequestDto;
import com.ssafy.user.dto.UserLoginRequestDto;
import com.ssafy.user.dto.UserModifyRequestDto;
import com.ssafy.user.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    int getUserCount();
    UserResponseDto getById(int id);
    boolean isDuplicatedId(String loginId);

    ResponseEntity<Void> save(UserJoinRequestDto requestDto);
    void update(UserModifyRequestDto requestDto);

    void delete(int userId);

    ResponseEntity<Map<String, Object>> login(UserLoginRequestDto requestDto);

    ResponseEntity<Map<String, Object>> getInfo(String loginId, String authorization);

    ResponseEntity<Map<String, Object>> refresh(String loginId, String refreshToken);

    ResponseEntity<Map<String, Object>> logout(String loginId);
}
