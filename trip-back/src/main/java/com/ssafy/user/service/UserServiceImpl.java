package com.ssafy.user.service;

import com.ssafy.user.dto.*;
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
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        if(status == HttpStatus.OK){
            log.debug("[UserService] AccessToken 검증 완료");
            UserResponseDto user = userMapper.getByLoginId(loginId);
            resultMap.put("userInfo", user);
        } else{
            log.error("[UserService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
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
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        if(status==HttpStatus.OK){
            log.debug("[UserService] AccessToken 검증 완료");
            userMapper.update(loginId, requestDto);
            status = HttpStatus.ACCEPTED;
            resultMap.put("message", "정상적으로 업데이트되었습니다.");
        } else{
            log.error("[UserService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> delete(String loginId, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        if(status==HttpStatus.OK){
            log.debug("[UserService] AccessToken 검증 완료");
            userMapper.delete(loginId);
            status = HttpStatus.NO_CONTENT;
            resultMap.put("message", "정상적으로 삭제되었습니다.");
        } else{
            log.error("[UserService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
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
                createTokens(user.getLoginId(), resultMap);
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
    private void createTokens(String loginId, Map<String, Object> resultMap) {
        log.debug("[UserService] 토큰 생성 중");
        String accessToken = jwtUtil.createAccessToken(loginId);
        String refreshToken = jwtUtil.createRefreshToken(loginId);
        log.debug("[UserService] accessToken: {}", accessToken);
        log.debug("[UserService] refreshToken: {}", refreshToken);

        saveRefreshToken(loginId, refreshToken);

        resultMap.put("access-token", accessToken);
        resultMap.put("refresh-token", refreshToken);
    }

    //DB에 RefreshToken 저장
    private void saveRefreshToken(String loginId, String refreshToken) {
        userMapper.saveRefreshToken(loginId, refreshToken);
        log.debug("[UserService] refreshToken 저장 완료: {}", refreshToken);
    }




    @Override
    public ResponseEntity<Map<String, Object>> refresh(String loginId, String refreshToken) {
        Map<String, Object> resultMap = new HashMap<>();
        refreshToken = refreshToken.replaceAll("Bearer ", "");
        log.debug("[UserService] RefreshToken 검증 시작 - {}", refreshToken);
        HttpStatus status = jwtUtil.checkToken(loginId, refreshToken);
        if (status==HttpStatus.OK) {
            if (refreshToken.equals(getRefreshToken(loginId))) {
                log.debug("[UserService] RefreshToken 검증 완료 - {}", refreshToken);
                log.debug("[UserService] AccessToken 재발급 시작");
                String accessToken = jwtUtil.createAccessToken(loginId);
                log.debug("[UserService] AccessToken 재발급 완료 - {}", accessToken);
                status = HttpStatus.CREATED;
                resultMap.put("access-token", accessToken);
            }else{
                log.debug("[UserService] RefreshToken - loginId 불일치");
            }
        } else{
            log.debug("[UserService] 유효하지 않은 RefreshToken");
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    private String getRefreshToken(String loginId) {
        return userMapper.getRefreshTokenByLoginId(loginId);
    }

    @Override
    public ResponseEntity<Map<String, Object>> logout(String loginId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.NO_CONTENT;
        try {
            log.debug("[UserService] RefreshToken 삭제 요청");
            deleteRefreshToken(loginId);
            log.debug("[UserService] RefreshToken 삭제 완료");
        } catch (Exception e) {
            log.error("[UserService] RefreshToken 삭제 실패");
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getPassword(UserPasswordRequestDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.debug("[UserService] 비밀번호 조회 요청");
            String password = userMapper.getPassword(requestDto);
            if(password==null){
                status = HttpStatus.BAD_REQUEST;
            }
            resultMap.put("password", password);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    private void deleteRefreshToken(String loginId) {
        userMapper.deleteRefreshToken(loginId);
    }

}
