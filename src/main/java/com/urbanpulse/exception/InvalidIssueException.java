package com.urbanpulse.exception;

public class InvalidIssueException extends RuntimeException {
    public InvalidIssueException(String message) {
        super(message);
    }
}