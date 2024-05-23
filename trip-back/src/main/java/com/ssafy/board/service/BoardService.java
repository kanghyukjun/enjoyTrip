package com.ssafy.board.service;

import com.ssafy.board.dto.BoardRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BoardService {
    ResponseEntity<Map<String, Object>> add(String loginId, BoardRequestDto requestDto, String authorization);

    ResponseEntity<Map<String, Object>> get(int page, String word);

    ResponseEntity<Map<String, Object>> getDetail(int boardId);

    ResponseEntity<Map<String, Object>> update(String loginId, int boardId, BoardRequestDto requestDto, String authorization);

    ResponseEntity<Map<String, Object>> delete(String loginId, int boardId, String authorization);

    ResponseEntity<Map<String, Object>> getUserBoard(String loginId, HttpServletRequest request);

    ResponseEntity<Map<String, Object>> addCourse(int courseId, String loginId, String authorization);

    ResponseEntity<Map<String, Object>> getHitBoard();
}
