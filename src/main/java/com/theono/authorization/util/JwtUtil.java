package com.theono.authorization.util;

import com.theono.authorization.constant.ErrorCase;
import com.theono.authorization.exception.ErrorStatusException;
import com.theono.authorization.property.JwtProperty;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

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
//                    .signWith(JwtProperty.secretKey) //이렇게 하면 default 알고리즘 HS512
                    .signWith(JwtProperty.secretKey, JwtProperty.signatureAlgorithm)
                    .compact();
        } catch (Exception ex) {
            System.out.println(ex);
            throw new ErrorStatusException(ErrorCase._401_AUTHENTICATION_FAIL); //TODO 이 Exception 처리 다시 생각
        }

        return generatedToken;
    }

}
