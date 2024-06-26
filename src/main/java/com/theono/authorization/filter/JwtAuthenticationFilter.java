package com.theono.authorization.filter;

import com.theono.authorization.exception.ErrorStatusException;
import com.theono.authorization.service.CustomUserDetailsService;
import com.theono.authorization.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access-token".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
            }
        }

        if(accessToken==null) { //access token이 null
            filterChain.doFilter(request, response);
        }else{
            Claims claims = JwtUtil.validateAccessToken(accessToken);
            if(claims.getSubject().equals("access")){
                String userId = claims.get("userId", String.class);
                Authentication authResult = authenticate(userId);
                onSuccessfulAuthentication(authResult, request, response, filterChain);
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }


    private Authentication authenticate(String userId) throws ErrorStatusException {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(
                userId);
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    private void onSuccessfulAuthentication(Authentication authResult, HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authResult);
        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }


}
