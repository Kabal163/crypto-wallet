package com.github.kabal163.balance;

public class FlushWaitingException extends RuntimeException {

    public FlushWaitingException(String message) {
        super(message);
    }

    public FlushWaitingException(String message, Throwable cause) {
        super(message, cause);
    }
}
