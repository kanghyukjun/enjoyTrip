package com.ssafy.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(title = "여행지 등록 요청 DTO", description = "여행지 등록에 필요한 정보가 있는 DTO이다.")
public class AddSpotRequestDto {

    @Schema(title = "여행지명", example = "멀티캠퍼스 역삼")
    private String title;

    @Schema(title = "주소", example = "서울 강남구 언주로 508")
    private String address;

    @Schema(title = "우편번호", example = "06152")
    private String zipcode;

    @Schema(title = "이미지")
    private String image;

    @Schema(title = "위도", example = "127.042909941112")
    private double latitude;

    @Schema(title = "경도", example = "37.5039660612952")
    private double longitude;

    @Schema(title = "시도 ID", example = "1")
    private int sidoId;

    @Schema(title = "구군 ID", example = "1")
    private int gugunId;

    @Schema(title = "타입 ID", example = "12")
    private int typeId;


}
