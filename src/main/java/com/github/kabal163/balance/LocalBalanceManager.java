package com.github.kabal163.balance;

import java.math.BigDecimal;

/**
 * Provides convenient methods to work with {@link LocalBalance local balance}.
 * Manages local balance's lifecycle.
 */
public interface LocalBalanceManager {

    /**
     * Increments thread's {@link LocalBalance local balance} and
     * returns resulted amount.
     *
     * @param value amount which must be incremented to the current local balance
     * @return resulted local balance amount
     */
    BigDecimal incrementBalance(BigDecimal value);

    /**
     * Counts and returns a total balance of all {@link LocalBalance local balances} and
     * resets local balances to it's default state.
     *
     * @return total balance
     */
    BigDecimal countAndReset();
}
