package com.github.kabal163.balance;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a snapshot of a wallet's balance in the specific time
 */
@Data
@Builder
public class BalanceSnapshot {

    /**
     * The moment when amount of balance has been captured
     */
    private final LocalDateTime datetime;

    /**
     * Amount of balance at specified time
     */
    private final BigDecimal amount;
}
