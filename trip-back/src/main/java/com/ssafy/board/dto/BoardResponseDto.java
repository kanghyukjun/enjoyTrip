package com.ssafy.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(title = "게시물 정보 DTO", description = "게시물 상세 정보가 있는 DTO이다.")
public class BoardResponseDto {

    @Schema(title = "게시물 ID", example = "1")
    private String id;

    @Schema(title = "게시물명", example = "한강 산책")
    private String title;

    @Schema(title = "내용", example = "한강 산책합니다")
    private String content;

    @Schema(title = "작성자", example = "혁준이")
    private String author;

    @Schema(title = "작성자 loginID", example = "ssafy")
    private String authorLoginId;

    @Schema(title = "작성일", example = "2024-05-20")
    private LocalDate createTime;

    @Schema(title = "조회수", example = "1")
    private int hit;

    @Schema(title = "썸네일 이미지", example = "2024-05-21")
    private String thumbnail;
}
