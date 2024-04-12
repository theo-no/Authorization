package com.theono.authorization.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theono.authorization.constant.ErrorCase;
import com.theono.authorization.model.response.ErrorResponse;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RequestResponseUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T jsonToObject(ServletInputStream inputStream, Class<T> type)
            throws IOException {
        return objectMapper.readValue(inputStream, type);
    }

    // TODO: After setting https, should modify this method to set secure
    public static void addCookieWithHttpOnly(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static <T> String jsonFrom(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static void setContentTypeToJson(HttpServletResponse response) {
        response.setContentType("application/json");
    }

    public static void setResponseToErrorResponse(HttpServletResponse response, ErrorCase errorCase)
            throws IOException {
        setContentTypeToJson(response);
        response.setStatus(errorCase.getStatus().value());
        String body =
                jsonFrom(ErrorResponse.builder().errorCode(errorCase.getErrorCode()).errorMessage(errorCase.getErrorMessage()).build());
        response.getWriter().write(body);
    }
}
