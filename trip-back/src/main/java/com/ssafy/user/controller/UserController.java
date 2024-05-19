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

    @GetMapping("/{loginId}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("loginId") String loginId,
                                                   HttpServletRequest request) {
        return userService.getInfo(loginId, request.getHeader("Authorization"));

    }

    @GetMapping("/join/{loginId}")
    public ResponseEntity<Boolean> duplicateIdCheck(@PathVariable("loginId") String loginId) {
        boolean isDuplicate = userService.isDuplicatedId(loginId);
        return ResponseEntity.ok(isDuplicate);
    }

    @PutMapping("/{loginId}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("loginId") String loginId,
                                                  @RequestBody UserModifyRequestDto requestDto,
                                                  HttpServletRequest request) {
        return userService.update(loginId, requestDto, request.getHeader("Authorization"));

    }

    @DeleteMapping("/{loginId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("loginId") String loginId, HttpServletRequest request) {
        return userService.delete(loginId, request.getHeader("Authorization"));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody UserJoinRequestDto requestDto) {
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


    @PostMapping("/refresh/{loginId}")
    public ResponseEntity<Map<String, Object>> refreshToken(@PathVariable("loginId") String loginId,
                                                            HttpServletRequest request){
        return userService.refresh(loginId, request.getHeader("refreshToken"));
    }
}
