package com.ssafy.course.dto;

import com.ssafy.trip.dto.SpotResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(title = "코스 상세 정보 반환 DTO", description = "등록한 코스 정보가 있는 DTO이다.")
public class CourseResponseDto {

    @Schema(title = "코스 ID", example = "1")
    private int id;

    @Schema(title = "코스명", example = "한강 산책")
    private String title;

    @Schema(title = "코스 설명", example = "간단한 한강 산책로 코스입니다.")
    private String desc;

    @Schema(title = "출발일", example = "2024-05-20")
    private LocalDate startDate;

    @Schema(title = "도착일", example = "2024-05-21")
    private LocalDate endDate;
}
