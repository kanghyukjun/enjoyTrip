package com.ssafy.user.controller;

import com.ssafy.user.dto.UserJoinRequestDto;
import com.ssafy.user.dto.UserModifyRequestDto;
import com.ssafy.user.dto.UserResponseDto;
import com.ssafy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    //TODO: JWT를 통해 유저를 조회하는 기능 구현(@AuthenticationPrincipal)
    @GetMapping
    public ResponseEntity<UserResponseDto> getInfo() {
        int userId = 1;
        UserResponseDto user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/join/{loginId}")
    public ResponseEntity<Boolean> duplicateIdCheck(@PathVariable("loginId") String loginId) {
        boolean isDuplicate = userService.duplicateIdCheck(loginId);
        if (isDuplicate) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @PatchMapping
    public ResponseEntity<UserResponseDto> modifyUserInfo(@RequestBody UserModifyRequestDto requestDto) {
        userService.update(requestDto);
        int userId = 3;
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserInfo() {
        int userId = 3;
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> joinUser(@RequestBody UserJoinRequestDto requestDto) {
        log.info(requestDto.toString());
        return null;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return null;
    }

}
