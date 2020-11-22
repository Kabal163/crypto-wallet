package com.github.kabal163.balance;

/**
 * Service is responsible for synchronizing in-memory buffered
 * balances and database entity's balance.
 * Each thread writes the balance to it's own {@link LocalBalance local balance}.
 * This service provides methods to flush the balance buffer to the
 * database.
 */
public interface BalanceFlushingService {

    /**
     * Flushes the buffered local balances to the database.
     */
    void flush();
}
