package com.theono.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception{

        http
                .securityMatchers((auth) -> auth.requestMatchers("/user"))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/user").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception{

        http
                .securityMatchers((auth) -> auth.requestMatchers("/admin"))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/admin").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

}
