package com.ssafy.user.controller;

import com.ssafy.trip.dto.SidoResponseDto;
import com.ssafy.user.dto.*;
import com.ssafy.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.io.Console;
import java.util.Map;

@RestController
@Tag(name = "02-1. 유저 관련 API", description = "유저 CRUD 및 로그인 API")
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{loginId}")
    @Operation(summary = "유저 정보 조회", description = "로그인 ID를 통해 유저 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회에 성공했습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "조회에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> get(@PathVariable("loginId") String loginId,
                                                   HttpServletRequest request) {
        return userService.getInfo(loginId, request.getHeader("Authorization"));

    }

    @PostMapping("/pwd")
    @Operation(summary = "비밀번호 찾기", description = "아이디와 이메일을 통해 비밀번호를 찾는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "찾기에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "찾기에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> getPassword(@RequestBody UserPasswordRequestDto requestDto) {
        log.debug("{}", requestDto);
        return userService.getPassword(requestDto);
    }

    @Operation(summary = "아이디 중복 체크", description = "중복되는 아이디인지 체크한다.")
    @Parameter(name = "loginId",required = true, description = "로그인 ID", example = "ssafy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중복 체크에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "중복 체크에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/join/{loginId}")
    public ResponseEntity<Boolean> duplicateIdCheck(@PathVariable("loginId") String loginId) {
        boolean isDuplicate = userService.isDuplicatedId(loginId);
        return ResponseEntity.ok(isDuplicate);
    }

    @Operation(summary = "유저 정보 수정", description = "유저 정보를 수정한다.")
    @Parameter(name = "loginId",required = true, description = "로그인 ID", example = "ssafy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "수정에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{loginId}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("loginId") String loginId,
                                                  @RequestBody UserModifyRequestDto requestDto,
                                                  HttpServletRequest request) {
        return userService.update(loginId, requestDto, request.getHeader("Authorization"));

    }

    @Operation(summary = "유저 삭제", description = "유저를 삭제한다.")
    @Parameter(name = "loginId",required = true, description = "로그인 ID", example = "ssafy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "삭제에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{loginId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("loginId") String loginId, HttpServletRequest request) {
        return userService.delete(loginId, request.getHeader("Authorization"));
    }

    @Operation(summary = "회원가입", description = "유저를 등록한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "등록에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "등록에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody UserJoinRequestDto requestDto) {
        return userService.save(requestDto);
    }

    @Operation(summary = "로그인", description = "로그인 요청을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "로그인에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "아이디와 비밀번호가 올바르지 않습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "로그인에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequestDto requestDto) {
        log.debug("[UserController]: 로그인 요청 정보 - {}", requestDto);
        return userService.login(requestDto);
    }

    @Operation(summary = "로그아웃", description = "로그아웃 요청을 한다.")
    @Parameter(name = "loginId",required = true, description = "로그인 ID", example = "ssafy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "로그아웃에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "로그아웃에 실패했습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/logout/{loginId}")
    public ResponseEntity<Map<String, Object>> logout(@PathVariable("loginId") String loginId) {
        return userService.logout(loginId);
    }

    @Operation(summary = "AccessToken 갱신 요청", description = "AccessToken 재발급 요청을 한다.")
    @Parameter(name = "loginId",required = true, description = "로그인 ID", example = "ssafy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "갱신에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "만료된 토큰입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "갱신에 실패했습니다.", content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/refresh/{loginId}")
    public ResponseEntity<Map<String, Object>> refreshToken(@PathVariable("loginId") String loginId,
                                                            HttpServletRequest request){
        return userService.refresh(loginId, request.getHeader("Authorization"));
    }
}
