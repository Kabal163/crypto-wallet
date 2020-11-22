package com.github.kabal163.balance;

import java.math.BigDecimal;

/**
 * Thread safe.
 * Represents a per thread balance.
 */
public class LocalBalance {

    private final Object lock = new Object();
    private BigDecimal amount;

    public LocalBalance() {
        this(new BigDecimal(0));
    }

    public LocalBalance(BigDecimal initAmount) {
        this.amount = initAmount;
    }

    public BigDecimal deposit(BigDecimal value) {
        if (value.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Deposited value must not be negative! actual: " + value);
        }
        synchronized (lock) {
            amount = amount.add(value);
            return amount;
        }
    }

    public BigDecimal reset() {
        synchronized (lock) {
            final BigDecimal tmp = amount;
            amount = new BigDecimal(0);
            return tmp;
        }
    }
}
