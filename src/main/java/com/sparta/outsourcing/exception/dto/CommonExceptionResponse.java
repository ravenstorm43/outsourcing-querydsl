package com.sparta.outsourcing.exception.dto;

import lombok.Getter;

@Getter
public class CommonExceptionResponse {
    private String errorMessage;
    private int httpStatusCode;

    public CommonExceptionResponse(String errorMessage, int httpStatusCode) {
        this.errorMessage = errorMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
