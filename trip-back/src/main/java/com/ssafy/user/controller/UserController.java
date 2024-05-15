package com.ssafy.user.controller;

import com.ssafy.user.dto.UserLoginRequestDto;
import com.ssafy.user.dto.UserLoginResponseDto;
import com.ssafy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{userid}")
    public ResponseEntity<UserLoginResponseDto> showUserInfo(@PathVariable("userid") int userid) {
        UserLoginResponseDto user = userService.getUserById(userid);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/join/{loginid}")
    public ResponseEntity<Boolean> isLoginIdExist(@PathVariable("loginid") String loginId) {
        boolean isIdExists = userService.getUserExistsByLoginId(loginId);
        if (isIdExists) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @PatchMapping("/{userid}")
    public ResponseEntity<?> modifyUserInfo() {
        return null;
    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<?> deleteUserInfo() {
        return null;
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinUser(@RequestBody UserLoginRequestDto userLoginRequestDto) {

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
