package com.github.kabal163.wallet;

import com.github.kabal163.balance.BalanceFlushingService;
import com.github.kabal163.transfer.ImmutableTransfer;

/**
 * Service provides with operations which can be executed
 * over a wallet
 */
public interface WalletCommandService {

    /**
     * Deposits money to a wallet according the {@code command} and
     * returns an immutable copy of created transfer
     * It's fast enough and can serve multiple deposits simultaneously.
     * Result balance will not be stored in database immediatly
     * but when {@link BalanceFlushingService#flush()} is completed.
     *
     * @param command contains information about depositing transfer. Must not be {@code null}
     * @return immutable copy of newly created transfer
     * @throws IllegalArgumentException if {@code command} argument is {@code null}
     */
    ImmutableTransfer deposit(DepositCommand command);
}
