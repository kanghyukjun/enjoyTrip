package com.ssafy.user.mapper;

import com.ssafy.user.dto.UserLoginResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int getUserCount() throws Exception;
    UserLoginResponseDto getUserLoginResponseDto(int userId) throws Exception;
    boolean idCheck(String loginId) throws Exception;
}
