package com.ssafy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Schema(title = "로그인 요청 DTO", description = "로그인 시 필요한 아이디, 비밀번호가 있는 DTO이다.")
public class UserLoginRequestDto {

    @Schema(title = "로그인 아이디", example = "ssafy")
    private String loginId;

    @Schema(title = "로그인 비밀번호", example = "1234")
    private String password;
}
