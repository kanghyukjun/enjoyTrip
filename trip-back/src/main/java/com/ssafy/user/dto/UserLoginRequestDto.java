package com.ssafy.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserLoginRequestDto {
    private String loginId;
    private String loginPassword;
}
