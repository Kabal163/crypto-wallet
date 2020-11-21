package com.github.kabal163.wallet;

import com.github.kabal163.transfer.ImmutableTransfer;

/**
 * Service provides with operations which can be executed
 * over a wallet
 */
public interface WalletCommandService {

    /**
     * Deposits money to a wallet according the {@code command} and
     * returns an immutable copy of created transfer
     *
     * @param command contains information about depositing transfer Must not be {@code null}
     * @return immutable copy of newly created transfer
     * @throws IllegalArgumentException if {@code command} argument is {@code null}
     */
    ImmutableTransfer deposit(DepositCommand command);
}
