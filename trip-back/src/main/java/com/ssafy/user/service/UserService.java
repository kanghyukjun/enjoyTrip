package com.ssafy.user.service;

import com.ssafy.user.dto.UserModifyRequestDto;
import com.ssafy.user.dto.UserResponseDto;

public interface UserService {
    int getUserCount();
    UserResponseDto getUserById(int id);

    boolean duplicateIdCheck(String loginId);
    void update(UserModifyRequestDto requestDto);

    void delete(int userId);
}
