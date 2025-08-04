package com.cabreu.candidatesmanagementservice.infrastructure.exception;


public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
