package com.github.kabal163.balance;

import java.time.LocalDateTime;
import java.util.List;

public interface BalanceRepository {

    List<BalanceSnapshot> getAggregatedHistory(LocalDateTime start, LocalDateTime end);
}
