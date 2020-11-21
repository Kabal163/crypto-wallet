package com.github.kabal163.balance;

import java.util.List;

public interface BalanceQueryService {

    /**
     * Searches and returns wallet's {@link BalanceSnapshot balance snapshots}
     * according the input {@link BalanceHistorySearchParams parametes}
     *
     * @return balance snapshots or empty list if nothing is found
     * @throws IllegalArgumentException if {@code params} argument is {@code null}
     */
    List<BalanceSnapshot> getBalanceHistory(BalanceHistorySearchParams params);
}
