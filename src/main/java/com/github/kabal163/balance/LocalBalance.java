package com.github.kabal163.balance;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Not thread safe
 */
@Data
public class LocalBalance {

    private BigDecimal amount = new BigDecimal(0);

    public BigDecimal deposit(BigDecimal value) {
        this.amount = this.amount.add(value);
        return this.amount;
    }

    public void reset() {
        amount = new BigDecimal(0);
    }
}
