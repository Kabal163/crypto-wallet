package com.github.kabal163.balance;

import java.math.BigDecimal;

public class LocalBalance {

    private final Object lock = new Object();
    private BigDecimal amount = new BigDecimal(0);

    public BigDecimal deposit(BigDecimal value) {
        synchronized (lock) {
            amount = amount.add(value);
            return amount;
        }
    }

    public BigDecimal reset() {
        synchronized (lock) {
            BigDecimal tmp = amount;
            amount = new BigDecimal(0);
            return tmp;
        }
    }
}
