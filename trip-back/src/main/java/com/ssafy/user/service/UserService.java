package com.ssafy.user.service;

import com.ssafy.user.dto.UserLoginResponseDto;

public interface UserService {
    int getUserCount();
    UserLoginResponseDto getUserById(int id);

    boolean getUserExistsByLoginId(String loginId);
}
