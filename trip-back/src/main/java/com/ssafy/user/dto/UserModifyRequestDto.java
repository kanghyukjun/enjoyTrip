package com.ssafy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "유저 정보 수정 요청 DTO", description = "수정할 유저정보가 있는 DTO이다.")
public class UserModifyRequestDto {

    @Schema(title = "유저 로그인 비밀번호", example = "1234")
    private String password;

    @Schema(title = "유저 닉네임", example = "강태경")
    private String name;

    @Schema(title = "유저 이메일", example = "ssafy@naver.com")
    private String email;
}
