package com.theono.authorization.util;

import com.fasterxml.jackson.databind.ObjectMapper;
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
}
