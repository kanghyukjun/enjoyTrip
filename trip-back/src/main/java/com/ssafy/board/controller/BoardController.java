package com.ssafy.board.controller;

import com.ssafy.board.dto.BoardRequestDto;
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
                                                   @RequestBody BoardRequestDto requestDto,
                                                   HttpServletRequest request) {
        return boardService.add(loginId, requestDto, request.getHeader("Authorization"));
    }

    @PostMapping("/{courseId}/{loginId}")
    public ResponseEntity<Map<String, Object>> addCourse(@PathVariable("courseId") int courseId,
                                                   @PathVariable("loginId") String loginId,
                                                   HttpServletRequest request) {
        return boardService.addCourse(courseId, loginId, request.getHeader("Authorization"));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> get(@RequestParam("pgno") int pgno,
                                                   @RequestParam("word") String word) {
        return boardService.get(pgno, word);
    }

    @GetMapping("/user/{loginId}")
    public ResponseEntity<Map<String, Object>> getUserBoard(@PathVariable("loginId") String loginId,
                                                            @RequestParam("pgno") int pgno,
                                                            @RequestParam("word") String word,
                                                            HttpServletRequest request) {
        return boardService.getUserBoard(loginId, pgno, word, request);
    }


    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> getDetail(@PathVariable("boardId") int boardId) {
        return boardService.getDetail(boardId);
    }

    @PutMapping("/{boardId}/{loginId}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("boardId") int boardId,
                                                      @PathVariable("loginId") String loginId,
                                                      @RequestBody BoardRequestDto requestDto,
                                                      HttpServletRequest request) {
        log.debug("request = {}", requestDto);
        return boardService.update(loginId, boardId, requestDto, request.getHeader("Authorization"));
    }

    @DeleteMapping("/{boardId}/{loginId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("boardId") int boardId,
                                                      @PathVariable("loginId") String loginId,
                                                      HttpServletRequest request) {
        return boardService.delete(loginId, boardId, request.getHeader("Authorization"));
    }
}
