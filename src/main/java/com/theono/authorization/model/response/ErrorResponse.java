package com.theono.authorization.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;
}
