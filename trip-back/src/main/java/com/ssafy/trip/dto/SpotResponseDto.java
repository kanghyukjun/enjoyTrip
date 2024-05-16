package com.ssafy.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SpotResponseDto {

    @Schema(title = "관광지 ID", example = "1")
    private int id;

    @Schema(title = "관광지 이름", example = "해운대")
    private String title;

    @Schema(title = "관광지 주소", example = "부산 중구")
    private String addr;

    @Schema(title = "관광지 이미지", example = "http://tong.visitkorea.or.kr/cms/resource/60/2678560_image2_1.jpg")
    private String img;

    @Schema(title = "관광지 위도", example = "37.52146325000000000")
    private double latitude;

    @Schema(title = "관광지 경도", example = "127.03381170000000000")
    private double longitude;

    @Schema(title = "관광지 유형", example = "12")
    private String type;
}
