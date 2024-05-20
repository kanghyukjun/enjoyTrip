package com.ssafy.trip.service;

import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.AddSpotRequestDto;
import com.ssafy.trip.dto.SpotResponseDto;
import com.ssafy.trip.mapper.TripMapper;
import com.ssafy.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService{

    private final TripMapper tripMapper;
    private final JWTUtil jwtUtil;

    @Override
    public List<SidoResponseDto> searchSido() throws Exception{
        return tripMapper.searchSido();
    }

    @Override
    public List<GugunResponseDto> searchGugun(int sidoCode) throws Exception{
        return tripMapper.searchGugun(sidoCode);
    }

    @Override
    public List<SpotResponseDto> searchSpot(int sidoCode, int gugunCode, List<String> spotType, String word) throws Exception{
        return tripMapper.searchSpot(sidoCode, gugunCode, spotType, word);
    }

    @Override
    public ResponseEntity<Map<String, Object>> addSpot(String loginId, AddSpotRequestDto requestDto, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        if(jwtUtil.checkToken(loginId, authorization)){
            log.debug("[UserService] AccessToken 검증 완료");
            try {
                tripMapper.addSpot(requestDto);
                resultMap.put("message", "여행지가 추가되었습니다.");
                status = HttpStatus.CREATED;
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
}
