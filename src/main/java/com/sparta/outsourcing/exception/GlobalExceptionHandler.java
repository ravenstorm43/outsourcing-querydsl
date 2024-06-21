package com.sparta.outsourcing.exception;

import com.sparta.outsourcing.exception.dto.CommonExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonExceptionResponse> validationException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder builder = new StringBuilder();

        FieldError fieldError = bindingResult.getFieldErrors().get(0);
        String fieldName = fieldError.getField();

        builder.append("[");
        builder.append(fieldName);
        builder.append("](은)는 ");
        builder.append(fieldError.getDefaultMessage());
        builder.append(" / 입력된 값: [");
        builder.append(fieldError.getRejectedValue());
        builder.append("]");

        CommonExceptionResponse response = new CommonExceptionResponse(builder.toString(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

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
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonExceptionResponse> notFoundException(NotFoundException ex) {
        log.error("NotFoundException : {}", ex.getMessage());
        CommonExceptionResponse response = new CommonExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<CommonExceptionResponse> incorrectPasswordException(IncorrectPasswordException ex) {
        log.error("IncorrectPasswordException : {}", ex.getMessage());
        CommonExceptionResponse response = new CommonExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
