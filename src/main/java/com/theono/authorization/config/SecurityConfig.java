package com.theono.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /**
     * user 관련 필터
     */
    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception{

        http
                .securityMatchers((auth) -> auth.requestMatchers("/user"))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/user").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

    /**
     * admin 관련 필터
     */
    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception{

        http
                .securityMatchers((auth) -> auth.requestMatchers("/admin"))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/admin").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

    /**
     * 필터 거칠 필요 없는 요청
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("*/img/**");
    }

}
