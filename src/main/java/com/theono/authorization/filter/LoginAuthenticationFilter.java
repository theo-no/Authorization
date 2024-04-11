package com.theono.authorization.filter;

import com.theono.authorization.constant.ErrorCase;
import com.theono.authorization.exception.ErrorStatusException;
import com.theono.authorization.model.dto.CustomUserDetails;
import com.theono.authorization.model.request.LoginRequest;
import com.theono.authorization.util.RequestResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@AllArgsConstructor
public class LoginAuthenticationFilter extends OncePerRequestFilter {

    private final AntPathRequestMatcher requestMatcher =
            new AntPathRequestMatcher("/login", "POST");
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            LoginRequest loginRequest =
                    RequestResponseUtil.jsonToObject(request.getInputStream(), LoginRequest.class);
            String userId = (loginRequest.getUserId() == null) ? "" : loginRequest.getUserId();
            String password =
                    (loginRequest.getPassword() == null) ? "" : loginRequest.getPassword();

            Authentication authResult = attemptAuthentication(userId, password);
            if (authResult == null) {
                throw new ErrorStatusException(ErrorCase._401_AUTHENTICATION_FAIL);
            }
            successfulAuthentication(response, authResult);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private Authentication attemptAuthentication(String userId, String password)
            throws ErrorStatusException {
        if (userId.isEmpty() || password.isEmpty()) {
            throw new ErrorStatusException(ErrorCase._400_BAD_LOGIN_REQUEST);
        }
        UsernamePasswordAuthenticationToken authRequest =
                        UsernamePasswordAuthenticationToken.unauthenticated(userId, password);
        try {
            return authenticationManager.authenticate(authRequest);
        } catch (AuthenticationException ex) {
            throw new ErrorStatusException(ErrorCase._401_AUTHENTICATION_FAIL);
        }
    }

    private void successfulAuthentication(HttpServletResponse response, Authentication authResult)
            throws ErrorStatusException, IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        String userId = userDetails.getUserEntity().getUserId();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        RequestResponseUtil.addCookieWithHttpOnly(response, "access-token","abcdefg");
    }
}
