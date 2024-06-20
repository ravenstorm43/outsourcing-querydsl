package com.sparta.outsourcing.exception;

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
