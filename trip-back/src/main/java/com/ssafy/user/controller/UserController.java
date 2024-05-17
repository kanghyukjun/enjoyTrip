package com.ssafy.user.controller;

import com.ssafy.user.dto.UserJoinRequestDto;
import com.ssafy.user.dto.UserLoginRequestDto;
import com.ssafy.user.dto.UserModifyRequestDto;
import com.ssafy.user.dto.UserResponseDto;
import com.ssafy.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> get() {
        int userId = 1;
        UserResponseDto user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/join/{loginId}")
    public ResponseEntity<Boolean> duplicateIdCheck(@PathVariable("loginId") String loginId) {
        boolean isDuplicate = userService.isDuplicatedId(loginId);
        if (isDuplicate) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @PatchMapping
    public ResponseEntity<UserResponseDto> update(@RequestBody UserModifyRequestDto requestDto) {
        userService.update(requestDto);
        int userId = 3;
        return ResponseEntity.ok(userService.getById(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete() {
        int userId = 3;
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserJoinRequestDto requestDto) {
        log.info(requestDto.toString());
        return userService.save(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequestDto requestDto) {
        log.debug("[UserController]: 로그인 요청 정보 - {}", requestDto);
        return userService.login(requestDto);
    }

    @GetMapping("/logout/{loginId}")
    public ResponseEntity<Map<String, Object>> logout(@PathVariable("loginId") String loginId) {
        return userService.logout(loginId);
    }

    @GetMapping("/info/{loginId}")
    public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("loginId") String loginId,
                                                       HttpServletRequest request) {
        return userService.getInfo(loginId, request.getHeader("Authorization"));

    }

    @PostMapping("/refresh/{loginId}")
    public ResponseEntity<Map<String, Object>> refreshToken(@PathVariable("loginId") String loginId,
                                                            HttpServletRequest request){
        return userService.refresh(loginId, request.getHeader("refreshToken"));
    }
}
