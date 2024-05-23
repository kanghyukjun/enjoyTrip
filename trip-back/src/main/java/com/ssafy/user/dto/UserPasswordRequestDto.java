package com.ssafy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "비밀번호 찾기 요청 DTO", description = "비밀번호 찾기 요청에 필요한 정보가 있는 DTO이다.")
public class UserPasswordRequestDto {

    @Schema(title = "유저 로그인 아이디", example = "ssafy")
    private String loginId;

    @Schema(title = "유저 이메일", example = "ssafy@naver.com")
    private String email;
}
