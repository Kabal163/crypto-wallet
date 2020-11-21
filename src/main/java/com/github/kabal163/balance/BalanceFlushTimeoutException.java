package com.github.kabal163.balance;

public class BalanceFlushTimeoutException extends RuntimeException {

    public BalanceFlushTimeoutException(String message) {
        super(message);
    }

    public BalanceFlushTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
