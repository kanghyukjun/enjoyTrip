package com.ssafy.user.mapper;

import com.ssafy.user.dto.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int getUserCount() throws Exception;
    UserResponseDto getById(int userId) throws Exception;
    UserResponseDto getByLoginId(String loginId);
    UserResponseDto getByLoginIdAndPassword(UserLoginRequestDto requestDto);

    String getRefreshTokenByLoginId(String loginId);
    void save(UserJoinRequestDto requestDto);
    void update(String loginId, UserModifyRequestDto requestDto);
    void delete(String loginId);

    void saveRefreshToken(String loginId, String refreshToken);

    void deleteRefreshToken(String loginId);

    String getPassword(UserPasswordRequestDto requestDto);
}
