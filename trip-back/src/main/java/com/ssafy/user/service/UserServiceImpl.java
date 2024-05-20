package com.ssafy.user.service;

import com.ssafy.user.dto.UserJoinRequestDto;
import com.ssafy.user.dto.UserLoginRequestDto;
import com.ssafy.user.dto.UserModifyRequestDto;
import com.ssafy.user.dto.UserResponseDto;
import com.ssafy.user.mapper.UserMapper;
import com.ssafy.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;

    @Override
    public int getUserCount() {
        try {
            return userMapper.getUserCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserResponseDto getById(int id) {
        try {
            return userMapper.getById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isDuplicatedId(String loginId) {
        try {
            UserResponseDto user = userMapper.getByLoginId(loginId);
            if(user!=null){
                log.debug("[UserService] 아이디 중복: {}", loginId);
                return true;
            } else{
                log.debug("[UserService] 아이디 중복체크 완료: {}", loginId);
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getInfo(String loginId, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        if(jwtUtil.checkToken(loginId, authorization)){
            log.debug("[UserService] AccessToken 검증 완료");
            try {
                UserResponseDto user = userMapper.getByLoginId(loginId);
                resultMap.put("userInfo", user);
                status = HttpStatus.OK;
            } catch (Exception e) {
                log.error("[UserService] AccessToken 검증 실패 - 서버 에러");
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else{
            status = HttpStatus.UNAUTHORIZED;
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> save(UserJoinRequestDto requestDto) {
        if(!isDuplicatedId(requestDto.getLoginId())){
            userMapper.save(requestDto);
            log.debug("[UserService] 회원가입 완료: {}", requestDto);
        } else{
            log.debug("[UserService] 회원가입 실패: {}", requestDto);
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> update(String loginId, UserModifyRequestDto requestDto, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        if(jwtUtil.checkToken(loginId, authorization)){
            log.debug("[UserService] AccessToken 검증 완료");
            try {
                userMapper.update(loginId, requestDto);
                resultMap.put("message", "정상적으로 업데이트되었습니다.");
                status = HttpStatus.OK;
            } catch (Exception e) {
                log.error("[UserService] AccessToken 검증 실패 - 서버 에러");
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else{
            log.error("[UserService] AccessToken 만료");
            status = HttpStatus.UNAUTHORIZED;
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> delete(String loginId, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        if(jwtUtil.checkToken(loginId, authorization)){
            log.debug("[UserService] AccessToken 검증 완료");
            try {
                userMapper.delete(loginId);
                resultMap.put("message", "정상적으로 삭제되었습니다.");
                status = HttpStatus.NO_CONTENT;
            } catch (Exception e) {
                log.error("[UserService] AccessToken 검증 실패 - 서버 에러");
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else{
            log.error("[UserService] AccessToken 만료");
            status = HttpStatus.UNAUTHORIZED;
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> login(UserLoginRequestDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            //아이디&비밀번호 검증
            UserResponseDto user = userMapper.getByLoginIdAndPassword(requestDto);
            if (user!=null) {
                log.debug("[UserService] 로그인 성공");
                createTokens(user.getLoginId(), status, resultMap);
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                status = HttpStatus.UNAUTHORIZED;
            }
            return ResponseEntity.status(status).body(resultMap);
        } catch (Exception e) {
            log.error(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(status).body(resultMap);
        }
    }



    //로그인 성공 시 토큰 생성
    private void createTokens(String loginId, HttpStatus status, Map<String, Object> resultMap) {
        log.debug("[UserService] 토큰 생성 중");
        String accessToken = jwtUtil.createAccessToken(loginId);
        String refreshToken = jwtUtil.createRefreshToken(loginId);
        log.debug("[UserService] accessToken: {}", accessToken);
        log.debug("[UserService] refreshToken: {}", refreshToken);

        saveRefreshToken(loginId, refreshToken);

        resultMap.put("access-token", accessToken);
        resultMap.put("refresh-token", refreshToken);

        status = HttpStatus.CREATED;
    }

    //DB에 RefreshToken 저장
    private void saveRefreshToken(String loginId, String refreshToken) {
        userMapper.saveRefreshToken(loginId, refreshToken);
        log.debug("[UserService] refreshToken 저장 완료: {}", refreshToken);
    }




    @Override
    public ResponseEntity<Map<String, Object>> refresh(String loginId, String refreshToken) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        log.debug("[UserService] RefreshToken 검증 시작 - {}", refreshToken);
        if (jwtUtil.checkToken(loginId, refreshToken)) {
            log.debug("[UserService] RefreshToken 검증 완료 - {}", refreshToken);
            log.debug("[UserService] AccessToken 재발급 시작");
            if (refreshToken.equals(getRefreshToken(loginId))) {
                String accessToken = jwtUtil.createAccessToken(loginId);
                log.debug("[UserService] AccessToken 재발급 완료 - {}", accessToken);
                resultMap.put("access-token", accessToken);
                status = HttpStatus.CREATED;
            }
        } else{
            log.debug("[UserService] RefreshToken 만료");
            status = HttpStatus.UNAUTHORIZED;
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    private String getRefreshToken(String loginId) {
        return userMapper.getRefreshTokenByLoginId(loginId);
    }

    @Override
    public ResponseEntity<Map<String, Object>> logout(String loginId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            log.debug("[UserService] RefreshToken 삭제 요청");
            deleteRefreshToken(loginId);
            log.debug("[UserService] RefreshToken 삭제 완료");
            status = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            log.error("[UserService] RefreshToken 삭제 실패");
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    private void deleteRefreshToken(String loginId) {
        userMapper.deleteRefreshToken(loginId);
    }

}
