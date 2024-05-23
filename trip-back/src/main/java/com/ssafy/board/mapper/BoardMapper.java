package com.ssafy.board.mapper;

import com.ssafy.board.dto.BoardRequestDto;
import com.ssafy.board.dto.BoardResponseDto;
import com.ssafy.board.dto.BoardSimpleResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void add(BoardRequestDto requestDto);

    List<BoardSimpleResponseDto> get(int start, int number, String word);

    int count(String word);

    BoardResponseDto getDetail(int boardId);

    int getCourseId(int boardId);

    void update(int boardId, BoardRequestDto requestDto);

    void addHit(int boardId);

    void delete(int boardId);

    void deleteByCourseId(int courseId);

    List<BoardSimpleResponseDto> getUserBoard(int userId);

    List<BoardSimpleResponseDto> getHitBoard();
}
