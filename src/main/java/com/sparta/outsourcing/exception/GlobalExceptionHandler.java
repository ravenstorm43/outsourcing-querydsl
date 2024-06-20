package com.sparta.outsourcing.exception;

import com.sparta.outsourcing.exception.dto.CommonExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * [409] 이미 존재하는 값이 있는 경우
     * @param ex : ConflictException[custom]
     * @return : body{message, 409}, httpStatusCode
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CommonExceptionResponse> conflictException(ConflictException ex) {
        log.error("ConflictException : {}", ex.getMessage());
        CommonExceptionResponse response = new CommonExceptionResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * [403] 인증은 되었으나 접근 권한이 존재하지 않는 경우
     * @param ex : ForbiddenException[custom]
     * @return : body{message, 403}, httpStatusCode
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CommonExceptionResponse> forbiddenException(ForbiddenException ex) {
        log.error("ForbiddenException : {}", ex.getMessage());
        CommonExceptionResponse response = new CommonExceptionResponse(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
