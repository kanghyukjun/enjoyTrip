package com.ssafy.course.service;

import com.ssafy.course.dto.CourseRequestDto;
import com.ssafy.course.dto.CourseResponseDto;
import com.ssafy.course.dto.CourseSimpleResponseDto;
import com.ssafy.course.mapper.CourseMapper;
import com.ssafy.trip.dto.SpotResponseDto;
import com.ssafy.trip.mapper.TripMapper;
import com.ssafy.user.mapper.UserMapper;
import com.ssafy.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService{

    private final JWTUtil jwtUtil;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;

    @Override
    public ResponseEntity<Map<String, Object>> add(String loginId, CourseRequestDto requestDto,
                                                   String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        log.debug("[CourseService] AccessToken 검증 시작 - {}", authorization);
        if (status == HttpStatus.OK) {
            log.debug("[CourseService] AccessToken 검증 완료 - {}", authorization);
            courseMapper.add(userMapper.getByLoginId(loginId).getId(), requestDto.getTitle(), requestDto.getDesc(), requestDto.getStartDate(), requestDto.getEndDate());
            int courseId = courseMapper.getLastIdx();
            courseMapper.addSpots(courseId, requestDto.getSpotIds());
            status = HttpStatus.CREATED;
            resultMap.put("msg", "코스를 등록했습니다.");
        } else{
            log.debug("[CourseService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDetail(String loginId, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        log.debug("[CourseService] AccessToken 검증 시작 - {}", authorization);
        if (status==HttpStatus.OK) {
            log.debug("[CourseService] AccessToken 검증 완료 - {}", authorization);
            int userId = userMapper.getByLoginId(loginId).getId();
            List<CourseSimpleResponseDto> courses =  courseMapper.get(userId);
            log.debug("[CourseService] course 목록 조회 완료 - {}", courses);
            resultMap.put("courses", courses);
        } else{
            log.debug("[CourseService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDetail(String loginId, int courseId, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        log.debug("[CourseService] AccessToken 검증 시작 - {}", authorization);
        if (status==HttpStatus.OK) {
            log.debug("[CourseService] AccessToken 검증 완료 - {}", authorization);
            CourseResponseDto course =  courseMapper.getDetail(courseId);
            List<SpotResponseDto> spots = courseMapper.getSpots(courseId);
            log.debug("[CourseService] course 조회 완료 - {}", course);
            resultMap.put("course", course);
            resultMap.put("spots", spots);
        } else{
            log.debug("[CourseService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> update(String loginId, int courseId, CourseRequestDto requestDto, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        log.debug("[CourseService] AccessToken 검증 시작 - {}", authorization);
        if (status == HttpStatus.OK) {
            log.debug("[CourseService] AccessToken 검증 완료 - {}", authorization);
            //기존에 있던 코스 삭제
            courseMapper.deleteSpots(courseId);
            //수정된 코스 추가
            courseMapper.update(courseId, requestDto.getTitle(), requestDto.getDesc(), requestDto.getStartDate(), requestDto.getEndDate());
            courseMapper.addSpots(courseId, requestDto.getSpotIds());
            status = HttpStatus.ACCEPTED;
            resultMap.put("msg", "코스를 수정했습니다.");
        } else{
            log.debug("[CourseService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> delete(String loginId, int courseId, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        log.debug("[CourseService] AccessToken 검증 시작 - {}", authorization);
        if (status == HttpStatus.OK) {
            log.debug("[CourseService] AccessToken 검증 완료 - {}", authorization);
            //코스에 등록된 여행지 삭제
            courseMapper.deleteSpots(courseId);
            //코스 삭제
            courseMapper.delete(courseId);
            status = HttpStatus.NO_CONTENT;
            resultMap.put("msg", "코스를 삭제했습니다.");
        } else{
            log.debug("[CourseService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }
}
