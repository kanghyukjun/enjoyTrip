package com.ssafy.course.service;

import com.ssafy.course.dto.CourseRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CourseService {
    ResponseEntity<Map<String, Object>> add(String loginId, CourseRequestDto requestDto, String authorization);

    ResponseEntity<Map<String, Object>> getDetail(String loginId, String authorization);

    ResponseEntity<Map<String, Object>> getDetail(String loginId, int courseId, String authorization);

    CourseRequestDto getDetail(int courseId);

    ResponseEntity<Map<String, Object>> update(String loginId, int courseId, CourseRequestDto requestDto, String authorization);

    ResponseEntity<Map<String, Object>> delete(String loginId, int courseId, String authorization);
}
