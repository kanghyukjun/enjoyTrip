package com.ssafy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "유저 정보 반환 DTO", description = "유저 정보가 있는 DTO이다.")
public class UserResponseDto {

    @Schema(title = "유저 ID", example = "1")
    private int id;
    private String name;
    private String email;
    private String loginId;
    private String password;
}
