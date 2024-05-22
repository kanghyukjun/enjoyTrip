package com.ssafy.course.mapper;

import com.ssafy.course.dto.CourseResponseDto;
import com.ssafy.course.dto.CourseSimpleResponseDto;
import com.ssafy.trip.dto.SpotResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CourseMapper {
    void add(int userId, String title, String desc, LocalDate startDate, LocalDate endDate);
    int getLastIdx();
    void addSpots(int courseId, List<Integer> spotIds);

    List<CourseSimpleResponseDto> get(int userId);

    void deleteSpots(int courseId);

    void update(int courseId, String title, String desc, LocalDate startDate, LocalDate endDate);

    void delete(int courseId);

    CourseResponseDto getDetail(int courseId);

    List<SpotResponseDto> getSpots(int courseId);
}
