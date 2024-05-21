package com.ssafy.board.service;

import com.ssafy.board.dto.BoardRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BoardService {
    ResponseEntity<Map<String, Object>> add(String loginId, BoardRequestDto requestDto, String authorization);

    ResponseEntity<Map<String, Object>> get(int page, String word);

    ResponseEntity<Map<String, Object>> getDetail(int boardId);

    ResponseEntity<Map<String, Object>> update(String loginId, int boardId, BoardRequestDto requestDto, String authorization);
}
