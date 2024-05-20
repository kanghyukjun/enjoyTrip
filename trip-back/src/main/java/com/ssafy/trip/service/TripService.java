package com.ssafy.trip.service;

import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.AddSpotRequestDto;
import com.ssafy.trip.dto.SpotResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TripService {
   public List<SidoResponseDto> searchSido() throws Exception;

    List<GugunResponseDto> searchGugun(int sidoCode) throws Exception;

    List<SpotResponseDto> searchSpot(int sidoCode, int gugunCode, List<String> spotType, String word) throws Exception;

    ResponseEntity<Map<String, Object>> addSpot(String loginId, AddSpotRequestDto requestDto, String authorization);
}
