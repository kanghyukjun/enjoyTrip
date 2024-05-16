package com.ssafy.trip.service;

import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.SpotResponseDto;

import java.util.List;

public interface TripService {
   public List<SidoResponseDto> searchSido() throws Exception;

    List<GugunResponseDto> searchGugun(int sidoCode) throws Exception;

    List<SpotResponseDto> searchSpot(int sidoCode, int gugunCode, List<String> spotType) throws Exception;
}
