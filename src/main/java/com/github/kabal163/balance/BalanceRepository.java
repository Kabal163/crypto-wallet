package com.github.kabal163.balance;

import java.util.List;

public interface BalanceRepository {

    List<BalanceSnapshot> getAggregatedHistory(BalanceHistorySearchParams params);
}
