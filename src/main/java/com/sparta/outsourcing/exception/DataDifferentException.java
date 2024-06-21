package com.sparta.outsourcing.exception;

public class DataDifferentException extends RuntimeException {
    public DataDifferentException(String message) {
        super(message);
    }
}