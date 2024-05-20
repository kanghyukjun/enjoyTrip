package com.ssafy.course.service;

import com.ssafy.course.dto.AddCourseRequestDto;
import com.ssafy.course.mapper.CourseMapper;
import com.ssafy.user.mapper.UserMapper;
import com.ssafy.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService{

    private final JWTUtil jwtUtil;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;

    @Override
    public ResponseEntity<Map<String, Object>> addCourse(String loginId, AddCourseRequestDto requestDto,
                                                         String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        log.debug("[CourseService] AccessToken 검증 시작 - {}", authorization);
        if (jwtUtil.checkToken(loginId, authorization)) {
            log.debug("[CourseService] AccessToken 검증 완료 - {}", authorization);
            courseMapper.addCourse(userMapper.getByLoginId(loginId).getId(), requestDto.getTitle(), requestDto.getDesc(), requestDto.getStartDate(), requestDto.getEndDate());
            int courseId = courseMapper.getLastIdx();
            System.out.println("courseId = " + courseId);
            courseMapper.addCourseSpots(courseId, requestDto.getSpotIds());
            resultMap.put("msg", "코스를 등록했습니다.");
        } else{
            log.debug("[CourseService] AccessToken 검증 실패");
            status = HttpStatus.UNAUTHORIZED;
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }
}
