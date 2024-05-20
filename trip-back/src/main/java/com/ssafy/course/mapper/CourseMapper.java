package com.ssafy.course.mapper;

import com.ssafy.course.dto.AddCourseRequestDto;
import com.ssafy.trip.dto.AddSpotRequestDto;
import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.SpotResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CourseMapper {
    void addCourse(int userId, String title, String desc, LocalDate startDate, LocalDate endDate);
    int getLastIdx();
    void addCourseSpots(int courseId, List<Integer> spotIds);
}
