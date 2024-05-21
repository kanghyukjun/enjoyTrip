package com.ssafy.board.service;

import com.ssafy.board.dto.BoardCreateRequestDto;
import com.ssafy.board.dto.BoardResponseDto;
import com.ssafy.board.dto.BoardSimpleResponseDto;
import com.ssafy.board.mapper.BoardMapper;
import com.ssafy.course.dto.CourseResponseDto;
import com.ssafy.course.mapper.CourseMapper;
import com.ssafy.trip.dto.SpotResponseDto;
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
public class BoardServiceImpl implements BoardService{

    private final JWTUtil jwtUtil;
    private final BoardMapper boardMapper;
    private final CourseMapper courseMapper;

    @Override
    public ResponseEntity<Map<String, Object>> add(String loginId, BoardCreateRequestDto requestDto, String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        log.debug("[BoardService] AccessToken 검증 시작 - {}", authorization);
        HttpStatus status = jwtUtil.checkToken(loginId, authorization);
        if (status==HttpStatus.OK) {
            log.debug("[CourseService] AccessToken 검증 완료 - {}", authorization);
            status = HttpStatus.CREATED;
            boardMapper.add(requestDto);
            resultMap.put("msg", "코스를 등록했습니다.");
        } else{
            log.debug("[CourseService] AccessToken 검증 실패");
            resultMap.put("msg", "AccessToken 검증에 실패했습니다.");
        }
        return ResponseEntity.status(status).body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> get(int pgno, String word) {
        Map<String, Object> resultMap = new HashMap<>();
        log.debug("[BoardService] 게시글 목록 조회");
        List<BoardSimpleResponseDto> articles = boardMapper.get(pgno-1, 8, word);
        int count = boardMapper.count(word);
        resultMap.put("articles", articles);
        resultMap.put("count", count);
        return ResponseEntity.ok().body(resultMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDetail(int boardId) {
        Map<String, Object> resultMap = new HashMap<>();
        log.debug("[BoardService] 게시글 조회 - {}", boardId);
        BoardResponseDto article = boardMapper.getDetail(boardId);
        int courseId = boardMapper.getCourseId(boardId);
        CourseResponseDto course = courseMapper.getDetail(courseId);
        List<SpotResponseDto> spots = courseMapper.getSpots(courseId);
        resultMap.put("article", article);
        resultMap.put("course", course);
        resultMap.put("spots", spots);
        return ResponseEntity.ok().body(resultMap);
    }
}
