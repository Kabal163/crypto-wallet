package com.github.kabal163.balance;

import java.util.List;

public interface BalanceQueryService {

    /**
     * Searches and returns wallet's balance snapshots
     * @return
     */
    List<BalanceSnapshot> getBalanceHistory(BalanceHistorySearchParams params);
}
