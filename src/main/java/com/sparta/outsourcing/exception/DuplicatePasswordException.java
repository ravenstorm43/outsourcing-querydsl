package com.sparta.outsourcing.exception;

public class DuplicatePasswordException extends RuntimeException{
    public DuplicatePasswordException(String message) {
        super(message);
    }
}
