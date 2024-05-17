package com.ssafy.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JWTUtil {

    @Value("${jwt.signature-key}")
    private String signatureKey;

    @Value("${jwt.access-token.expiretime")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh-token.expiretime")
    private long refreshTokenExpireTime;

    public String createAccessToken(String userId) {
        log.debug("[JWTUtil]: AccessToken 생성 중");
        return create(userId, "access-token", accessTokenExpireTime);
    }

    public String createRefreshToken(String userId) {
        log.debug("[JWTUtil]: RefreshToken 생성 중");
        return create(userId, "refresh-token", refreshTokenExpireTime);
    }


    private String create(String userId, String subject, long expireTime){

        //Claim 설정
        //subject: 토큰제목
        //issuedAt: 토큰 생성일
        //expiration: 토큰 만료일
        Claims claims = Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime));

        claims.put("userId", userId);

        //jwt 생성
        ///헤더 정보 + claim + 서명 으로 구성
        String jwt = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .signWith(generateKey())
                .compact();
        log.debug("[JWTUtil]: Token 생성 완료");
        return jwt;
    }

    private Key generateKey() {
        byte[] keyBytes = signatureKey.getBytes(StandardCharsets.UTF_8);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    //토큰 검증
    public boolean checkToken(String token){
        try {
            log.debug("[JWTUtil]: Token 검증 중");
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token);
            log.debug("[JWTUtil]: claims - {}", claims);
            return true;
        }catch (ExpiredJwtException e){
            log.error("[JWTUtil]: 만료된 Token 입니다.");
            return false;
        }catch (Exception e){
            log.error("[JWTUtil]:  Token 검증에 실패 했습니다.");
            return false;
        }
    }
}
