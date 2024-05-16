package com.ssafy.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SidoResponseDto {

    @Schema(title = "시도 ID", example = "1")
    private int id;

    @Schema(title = "시도 이름", example = "서울특별시")
    private String name;
}
