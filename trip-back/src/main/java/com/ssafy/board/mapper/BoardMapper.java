package com.ssafy.board.mapper;

import com.ssafy.board.dto.BoardCreateRequestDto;
import com.ssafy.board.dto.BoardResponseDto;
import com.ssafy.board.dto.BoardSimpleResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void add(BoardCreateRequestDto requestDto);

    List<BoardSimpleResponseDto> get(int start, int number, String word);

    int count(String word);

    BoardResponseDto getDetail(int boardId);

    int getCourseId(int boardId);
}
