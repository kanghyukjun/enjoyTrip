package com.ssafy.user.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {
    private int userId;
    private String userName;
    private String userEmail;
    private String loginId;
    private String loginPassword;
}
