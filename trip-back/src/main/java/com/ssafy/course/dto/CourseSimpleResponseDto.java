package com.ssafy.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(title = "코스 목록 반환 DTO", description = "간단한 코스 정보들이 있는 DTO이다.")
public class CourseSimpleResponseDto {

    @Schema(title = "코스 ID", example = "1")
    private int id;

    @Schema(title = "코스 제목", example = "해운대")
    private String title;

    @Schema(title = "출발일", example = "2024-05-20")
    private LocalDate startDate;

    @Schema(title = "도착일", example = "2024-05-21")
    private LocalDate endDate;

}
