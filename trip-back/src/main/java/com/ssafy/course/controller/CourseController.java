package com.ssafy.course.controller;

import com.ssafy.course.dto.AddCourseRequestDto;
import com.ssafy.course.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "03-1. 여행코스 관련 API", description = "코스 CRUD API")
@RequiredArgsConstructor
@RequestMapping("/course")
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/{loginId}")
    public ResponseEntity<Map<String, Object>> addCourse(@PathVariable("loginId") String loginId, @RequestBody AddCourseRequestDto requestDto,
                                                         HttpServletRequest request) {
        log.debug("[CourseController] 코스 등록 요청 - {}", requestDto);
        return courseService.addCourse(loginId, requestDto, request.getHeader("Authorization"));
    }



}
