package com.theono.authorization.util;

import com.theono.authorization.constant.ErrorCase;
import com.theono.authorization.exception.ErrorStatusException;
import com.theono.authorization.property.JwtProperty;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    public static String generateAccessToken(String userId) throws ErrorStatusException {
        Date expiredDate = new Date(System.currentTimeMillis() + JwtProperty.accessValidTime);
        return generateToken(userId, expiredDate, "access");
    }

    public static String generateRefreshToken(String userId) throws ErrorStatusException {
        Date expiredDate = new Date(System.currentTimeMillis() + JwtProperty.refreshValidTime);
        return generateToken(userId, expiredDate, "refresh");
    }

    public static String generateToken(String userId, Date expiredDate, String subject)
            throws ErrorStatusException {

        String generatedToken;
        try {
            generatedToken = Jwts.builder()
                    .expiration(expiredDate)
                    .subject(subject)
                    .claim("userId", userId)
                    .signWith(JwtProperty.secretKey)
                    .compact();
        } catch (Exception ex) {
            throw new ErrorStatusException(ErrorCase._401_AUTHENTICATION_FAIL); //TODO 이 Exception 처리 다시 생각
        }

        return generatedToken;
    }

    public static Claims validateAccessToken(String accessToken) throws ErrorStatusException {

        try {
            JwtParser parser = Jwts.parser().verifyWith(JwtProperty.secretKey).build();
            System.out.println(parser);
            return parser.parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException ex) {
            throw new ErrorStatusException(ErrorCase._401_ACCESS_TOKEN_EXPIRED);
        } catch (Exception ex) {
            throw new ErrorStatusException(ErrorCase._401_INVALID_ACCESS_TOKEN);
        }

    }

    public static Claims validateRefreshToken(String refreshToken) throws ErrorStatusException {

        try {
            JwtParser parser = Jwts.parser().verifyWith(JwtProperty.secretKey).build();
            return parser.parseSignedClaims(refreshToken).getPayload();
        } catch (ExpiredJwtException ex) {
            throw new ErrorStatusException(ErrorCase._401_REFRESH_TOKEN_EXPIRED);
        } catch (Exception ex) {
            throw new ErrorStatusException(ErrorCase._401_INVALID_REFRESH_TOKEN);
        }

    }
}
