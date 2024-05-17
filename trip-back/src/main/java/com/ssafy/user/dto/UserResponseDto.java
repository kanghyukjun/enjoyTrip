package com.ssafy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserResponseDto {

    @Schema(title = "유저 ID", example = "1")
    private int id;
    private String name;
    private String email;
    private String loginId;
    private String password;
}
