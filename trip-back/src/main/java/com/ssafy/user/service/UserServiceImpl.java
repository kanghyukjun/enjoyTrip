package com.ssafy.user.service;

import com.ssafy.user.dto.UserModifyRequestDto;
import com.ssafy.user.dto.UserResponseDto;
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
    public UserResponseDto getUserById(int id) {
        try {
            return userMapper.getUser(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean duplicateIdCheck(String loginId) {
        try {
            return userMapper.idCheck(loginId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserModifyRequestDto requestDto) {
        try {
            int id=3;
            userMapper.updateUser(id, requestDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int userId) {
        try {
            userMapper.delete(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
