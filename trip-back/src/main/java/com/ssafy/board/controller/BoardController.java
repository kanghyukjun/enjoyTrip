package com.ssafy.board.controller;

import com.ssafy.board.dto.BoardCreateRequestDto;
import com.ssafy.board.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "04-1. 게시판 관련 API", description = "게시판 CRUD API")
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{loginId}")
    public ResponseEntity<Map<String, Object>> add(@PathVariable("loginId") String loginId,
                                                   @RequestBody BoardCreateRequestDto requestDto,
                                                   HttpServletRequest request) {
       return boardService.add(loginId, requestDto, request.getHeader("Authorization"));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> get(@RequestParam("pgno") int pgno,
                                                   @RequestParam("word") String word){
        return boardService.get(pgno, word);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> getDetail(@PathVariable("boardId") int boardId){
        return boardService.getDetail(boardId);
    }
}
