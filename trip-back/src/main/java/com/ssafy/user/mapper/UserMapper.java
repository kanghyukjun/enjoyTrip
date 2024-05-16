package com.ssafy.user.mapper;

import com.ssafy.user.dto.UserModifyRequestDto;
import com.ssafy.user.dto.UserResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int getUserCount() throws Exception;
    UserResponseDto getUser(int userId) throws Exception;
    boolean idCheck(String loginId) throws Exception;
    void updateUser(int userId, UserModifyRequestDto requestDto);

    void delete(int userId);
}
