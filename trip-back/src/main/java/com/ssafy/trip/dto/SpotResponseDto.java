package com.ssafy.trip.dto;

import lombok.Data;

@Data
public class SpotResponseDto {
    private int id;
    private String title;
    private String addr;
    private String img;
    private double latitude;
    private double longitude;
    private String type;
}
