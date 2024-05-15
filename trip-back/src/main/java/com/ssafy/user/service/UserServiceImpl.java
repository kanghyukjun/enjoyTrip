package com.ssafy.user.service;

import com.ssafy.user.dto.UserLoginResponseDto;
import com.ssafy.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserMapper userMapper;

    @Override
    public int getUserCount() {
        try {
            return userMapper.getUserCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserLoginResponseDto getUserById(int id) {
        try {
            return userMapper.getUserLoginResponseDto(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getUserExistsByLoginId(String loginId) {
        try {
            return userMapper.idCheck(loginId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
