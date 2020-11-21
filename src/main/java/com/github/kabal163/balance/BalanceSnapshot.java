package com.github.kabal163.balance;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BalanceSnapshot {

    private final LocalDateTime datetime;
    private final BigDecimal amount;
}
