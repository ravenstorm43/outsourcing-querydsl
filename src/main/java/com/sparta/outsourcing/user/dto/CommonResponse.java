package com.sparta.outsourcing.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private Integer httpStatusCode;
    private String message;
    private T data;

    public CommonResponse(String message, int httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public CommonResponse(String message, int httpStatusCode, T data) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
        this.data = data;
    }
}