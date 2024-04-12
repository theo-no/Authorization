package com.theono.authorization.property;

import com.theono.authorization.constant.Algorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtProperty {

    public static String secret;
    public static long accessValidTime;
    public static long refreshValidTime;
    public static SecretKey secretKey;

    @Value("#{environment['jwt.secret']}")
    public void setSecret(String secret) {JwtProperty.secret = secret;}

    @Value("#{environment['jwt.valid-time.access-expiration-milliseconds']}")
    public void setAccessValidTime(String accessValidTime) {
        JwtProperty.accessValidTime = Long.parseLong(accessValidTime.replaceAll("_",""));
    }

    @Value("#{environment['jwt.valid-time.refresh-expiration-milliseconds']}")
    public void setRefreshValidTime(String refreshValidTime) {
        JwtProperty.refreshValidTime = Long.parseLong(refreshValidTime.replaceAll("_",""));
    }

    @PostConstruct
    private void init() {
        secretKey =
                new SecretKeySpec(
                        secret.getBytes(StandardCharsets.UTF_8),
                        Jwts.SIG.HS256.key().build().getAlgorithm());
    }

}
