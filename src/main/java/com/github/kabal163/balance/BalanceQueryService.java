package com.github.kabal163.balance;

import java.time.LocalDateTime;
import java.util.List;

public interface BalanceQueryService {

    /**
     * Searches and returns wallet's balance snapshots
     * @return
     */
    List<BalanceSnapshot> getBalanceHistory(LocalDateTime start, LocalDateTime end);
}
