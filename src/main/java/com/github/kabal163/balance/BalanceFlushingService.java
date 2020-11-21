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
     * During this operation all threads must not change it's
     * local balance because any changes which occurs during the flushing
     * will be lost.
     * It order to prevent changes during the flushing use {@link #waitIfFlushIsRunning()}
     */
    void flush();

    /**
     * If flushing is running then thread will wait
     * until the end of the flushing.
     * If no flushing is running then nothing will happen.
     *
     * @throws FlushWaitingException if wait timeout is exceeded
     *                               or thread was interrupted while waiting the end of the flushing.
     */
    void waitIfFlushIsRunning();
}
