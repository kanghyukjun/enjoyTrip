package com.ssafy.trip.controller;

import com.ssafy.trip.dto.GugunResponseDto;
import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.trip.dto.SpotResponseDto;
import com.ssafy.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/trip")
@RequiredArgsConstructor
@RestController
public class TripController {

    private final TripService tripService;

    @GetMapping("/sido")
    public ResponseEntity<?> searchSido() throws Exception {
        List<SidoResponseDto> response = tripService.searchSido();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/gugun")
    public ResponseEntity<?> searchGugun(@RequestParam("sido") int sidoCode) throws Exception {
        List<GugunResponseDto> response = tripService.searchGugun(sidoCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/spot")
    public ResponseEntity<?> searchSpot(@RequestParam("sido") int sidoCode,
                                        @RequestParam("gugun") int gugunCode,
                                        @RequestParam("type") List<String> spotType) throws Exception {
        List<SpotResponseDto> response = tripService.searchSpot(sidoCode, gugunCode, spotType);
        return ResponseEntity.ok(response);
    }

}
