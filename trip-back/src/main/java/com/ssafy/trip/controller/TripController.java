package com.ssafy.trip.controller;

import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.AddSpotRequestDto;
import com.ssafy.trip.dto.SpotResponseDto;
import com.ssafy.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/trip")
@RequiredArgsConstructor
@Tag(name = "01-1. 관광지 조회 관련 API", description = "관광지를 검색하는 기능에 대한 API")
@RestController
public class TripController {

    private final TripService tripService;

    @GetMapping("/sido")
    @Operation(summary = "시, 도 정보 조회", description = "관광지 검색 시 필터에 사용되는 시, 도 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회에 성공했습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SidoResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "조회에 실패했습니다.", content = @Content(mediaType = "application/json")),
    })

    public ResponseEntity<?> searchSido() throws Exception {
        List<SidoResponseDto> response = tripService.searchSido();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/gugun")
    @Operation(summary = "구, 군 정보 조회", description = "관광지 검색 시 조건에 사용되는 구, 군 정보를 조회한다.")
    @Parameters(value = {@Parameter(name = "sido", required = true, description = "시도 코드", example = "1")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회에 성공했습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GugunResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "조회에 실패했습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<?> searchGugun(@RequestParam("sido") int sidoCode) throws Exception {
        List<GugunResponseDto> response = tripService.searchGugun(sidoCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/spot")
    @Operation(summary = "관광지 조회", description = "조건에 맞는 관광지들을 조회한다.")
    @Parameters(value = {@Parameter(name = "sido", required = true, description = "시도 코드", example = "1"),
            @Parameter(name = "gugun", required = true, description = "구군 코드", example = "1"),
            @Parameter(name = "type", required = false, description = "관광지 유형", example = "[문화시설, 관광지]"),

    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회에 성공했습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GugunResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "조회에 실패했습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<?> searchSpot(@RequestParam("sido") int sidoCode,
                                        @RequestParam("gugun") int gugunCode,
                                        @RequestParam("type") List<String> spotType,
                                        @RequestParam("word") String word) throws Exception {
        List<SpotResponseDto> response = tripService.searchSpot(sidoCode, gugunCode, spotType, word);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{loginId}")
    @Operation(summary = "관광지 등록", description = "관광지를 등록한다.")
    @Parameter(name = "loginId", required = true, description = "로그인Id", example = "ssafy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회에 성공했습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GugunResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "조회에 실패했습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<Map<String, Object>> addSpot(@PathVariable("loginId") String loginId,
                                                          @RequestBody AddSpotRequestDto requestDto,
                                                          HttpServletRequest request) {
        return tripService.addSpot(loginId, requestDto, request.getHeader("Authorization"));
    }

}
