package com.ssafy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    @Schema(title = "유저 ID", example = "1")
    private int userId;
    private String userName;
    private String userEmail;
    private String loginId;
    private String loginPassword;
}
