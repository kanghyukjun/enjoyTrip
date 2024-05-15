package com.ssafy.trip.mapper;

import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.SpotResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface TripMapper {
    List<SidoResponseDto> searchSido() throws SQLException;
    List<GugunResponseDto> searchGugun(int sidoCode) throws SQLException;
    List<SpotResponseDto> searchSpot(int sidoCode, int gugunCode, int spotType) throws SQLException;
}
