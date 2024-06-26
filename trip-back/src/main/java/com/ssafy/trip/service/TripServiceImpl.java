package com.ssafy.trip.service;

import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.AddSpotRequestDto;
import com.ssafy.trip.dto.SpotResponseDto;
import com.ssafy.trip.mapper.TripMapper;
import com.ssafy.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    private final TripMapper tripMapper;
    private final JWTUtil jwtUtil;

    @Override
    public List<SidoResponseDto> searchSido() throws Exception {
        return tripMapper.searchSido();
    }

    @Override
    public List<GugunResponseDto> searchGugun(int sidoCode) throws Exception {
        return tripMapper.searchGugun(sidoCode);
    }

    @Override
    public List<SpotResponseDto> searchSpot(int sidoCode, int gugunCode, List<String> spotType, String word) throws Exception {
        return tripMapper.searchSpot(sidoCode, gugunCode, spotType, word);
    }

    @Override
    public ResponseEntity<Map<String, Object>> addSpot(String loginId, AddSpotRequestDto requestDto, String authorization) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        if (status == HttpStatus.OK) {
            log.debug("[UserService] AccessToken 검증 완료");
            tripMapper.addSpot(requestDto);
            status = HttpStatus.CREATED;
            resultMap.put("message", "여행지가 추가되었습니다.");
        } else {
            log.error("[TripService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }
}
