package com.theono.authorization.controller;

import com.theono.authorization.model.dto.UserDto;
import com.theono.authorization.service.UserService;
import com.theono.authorization.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String userPage(){
        return "user";
    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<?> getUserInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access-token".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
            }
        }

        if(accessToken == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Claims claims = JwtUtil.validateAccessToken(accessToken);
        String userId = null;
        if(claims.getSubject().equals("access")){
            userId = claims.get("userId", String.class);
        }

        return userService.getUserInfo(userId);
    }
}
