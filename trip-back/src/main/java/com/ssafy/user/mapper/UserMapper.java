package com.ssafy.user.mapper;

import com.ssafy.user.dto.UserJoinRequestDto;
import com.ssafy.user.dto.UserLoginRequestDto;
import com.ssafy.user.dto.UserModifyRequestDto;
import com.ssafy.user.dto.UserResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int getUserCount() throws Exception;
    UserResponseDto getById(int userId) throws Exception;
    UserResponseDto getByLoginId(String loginId);
    UserResponseDto getByLoginIdAndPassword(UserLoginRequestDto requestDto);

    String getRefreshTokenByLoginId(String loginId);
    void save(UserJoinRequestDto requestDto);
    void update(int userId, UserModifyRequestDto requestDto);
    void delete(int userId);

    void saveRefreshToken(String loginId, String refreshToken);

    void deleteRefreshToken(String loginId);
}
