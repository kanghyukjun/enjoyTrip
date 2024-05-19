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

    ResponseEntity<Map<String, Object>> save(UserJoinRequestDto requestDto);
    ResponseEntity<Map<String, Object>> update(String loginId, UserModifyRequestDto requestDto, String authorization);

    ResponseEntity<Map<String, Object>> delete(String loginId, String authorization);

    ResponseEntity<Map<String, Object>> login(UserLoginRequestDto requestDto);

    ResponseEntity<Map<String, Object>> getInfo(String loginId, String authorization);

    ResponseEntity<Map<String, Object>> refresh(String loginId, String refreshToken);

    ResponseEntity<Map<String, Object>> logout(String loginId);
}
