package com.ssafy.course.service;

import com.ssafy.course.dto.AddCourseRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CourseService {
    ResponseEntity<Map<String, Object>> addCourse(String loginId, AddCourseRequestDto requestDto, String authorization);
}
