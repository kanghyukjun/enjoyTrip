package com.ssafy.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GugunResponseDto {

    @Schema(title = "구군 ID", example = "1")
    private int id;

    @Schema(title = "구군 이름", example = "강남구")
    private String name;

}
