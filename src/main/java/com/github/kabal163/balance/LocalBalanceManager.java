package com.github.kabal163.balance;

import java.math.BigDecimal;

/**
 * Provides convenient methods to work with {@link LocalBalance local balance}.
 * Manages local balance's lifecycle.
 * Should be thread safe
 */
public interface LocalBalanceManager {

    /**
     * Increments thread's {@link LocalBalance local balance} and
     * returns resulted amount.
     * Balance must not be incremented while {@link #countAndReset()} is running,
     * so be sure that you don't invoke it until the {@link #countAndReset()}
     * is completed
     *
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
