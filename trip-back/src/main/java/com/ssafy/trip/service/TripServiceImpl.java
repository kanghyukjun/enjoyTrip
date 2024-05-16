package com.ssafy.trip.service;

import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.SpotResponseDto;
import com.ssafy.trip.mapper.TripMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService{

    private final TripMapper tripMapper;

    @Override
    public List<SidoResponseDto> searchSido() throws Exception{
        return tripMapper.searchSido();
    }

    @Override
    public List<GugunResponseDto> searchGugun(int sidoCode) throws Exception{
        return tripMapper.searchGugun(sidoCode);
    }

    @Override
    public List<SpotResponseDto> searchSpot(int sidoCode, int gugunCode, List<String> spotType) throws Exception{
        return tripMapper.searchSpot(sidoCode, gugunCode, spotType);
    }
}
