package com.ssafy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequestDto {
    private String userName;
    private String userEmail;
    private String loginId;
    private String loginPassword;
}
