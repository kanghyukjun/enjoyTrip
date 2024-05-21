package com.ssafy.course.controller;

import com.ssafy.course.dto.CourseRequestDto;
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
    public ResponseEntity<Map<String, Object>> add(@PathVariable("loginId") String loginId,
                                                   @RequestBody CourseRequestDto requestDto,
                                                   HttpServletRequest request) {
        log.debug("[CourseController] 코스 등록 요청 - {}", requestDto);
        return courseService.add(loginId, requestDto, request.getHeader("Authorization"));
    }

    @GetMapping("/{loginId}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("loginId") String loginId,
                                                   HttpServletRequest request) {
        log.debug("[CourseController] 코스 조회 요청 - {}", loginId);
        return courseService.getDetail(loginId, request.getHeader("Authorization"));
    }

    @GetMapping("/{loginId}/{courseId}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("loginId") String loginId,
                                                   @PathVariable("courseId") int courseId,
                                                   HttpServletRequest request) {
        log.debug("[CourseController] 코스 상세 조회 요청 - {}", courseId);
        return courseService.getDetail(loginId, courseId, request.getHeader("Authorization"));
    }

    @PutMapping("/{loginId}/{courseId}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("loginId") String loginId,
                                                      @PathVariable("courseId") int courseId,
                                                      @RequestBody CourseRequestDto requestDto,
                                                      HttpServletRequest request) {
        log.debug("[CourseController] 코스 수정 요청 - {}", requestDto);
        return courseService.update(loginId, courseId, requestDto,request.getHeader("Authorization"));
    }

    @DeleteMapping("/{loginId}/{courseId}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("loginId") String loginId,
                                                      @PathVariable("courseId") int courseId,
                                                      HttpServletRequest request) {
        log.debug("[CourseController] 코스 삭제 요청 - {}", courseId);
        return courseService.delete(loginId, courseId,request.getHeader("Authorization"));
    }


}
