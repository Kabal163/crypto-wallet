package com.github.kabal163.balance;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Not thread safe
 */
@Data
public class BalanceContext {

    private BigDecimal balance = new BigDecimal(0);

    public BigDecimal deposit(BigDecimal value) {
        balance = balance.add(value);
        return balance;
    }

    public void reset() {
        balance = new BigDecimal(0);
    }
}
