package com.ssafy.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "게시물 생성 요청 DTO", description = "생성할 게시물 정보가 있는 DTO이다.")
public class BoardRequestDto {
    @Schema(title = "게시물명", example = "한강 산책")
    private String title;

    @Schema(title = "게시물 내용", example = "간단한 한강 산책로 코스입니다.")
    private String content;

    @Schema(title = "썸네일 이미지", example = "2024-05-21")
    private String thumbnail;

    @Schema(title = "코스 ID", example = "1")
    private int courseId;
}
