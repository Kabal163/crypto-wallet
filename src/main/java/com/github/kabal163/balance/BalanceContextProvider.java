package com.github.kabal163.balance;

import java.math.BigDecimal;

public interface BalanceContextProvider {

    BalanceContext newInstance();

    BigDecimal countAndReset();
}
