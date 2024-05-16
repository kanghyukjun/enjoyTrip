package com.ssafy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserModifyRequestDto {

    @Schema(title = "유저 로그인 비밀번호", example = "1234")
    private String password;

    @Schema(title = "유저 닉네임", example = "강태경")
    private String name;

    @Schema(title = "유저 이메일", example = "ssafy@naver.com")
    private String email;
}
