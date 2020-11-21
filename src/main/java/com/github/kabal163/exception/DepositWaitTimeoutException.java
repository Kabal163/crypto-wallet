package com.github.kabal163.exception;

public class DepositWaitTimeoutException extends RuntimeException {

    public DepositWaitTimeoutException(String message) {
        super(message);
    }

    public DepositWaitTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
