package com.ssafy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserLoginRequestDto {

    @Schema(title = "로그인 아이디", example = "ssafy")
    private String loginId;

    @Schema(title = "로그인 비밀번호", example = "1234")
    private String password;
}
