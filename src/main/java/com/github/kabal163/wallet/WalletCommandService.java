package com.github.kabal163.wallet;

import com.github.kabal163.transfer.ImmutableTransfer;

public interface WalletCommandService {

    /**
     * Creates a new transfer from the {@code command} and
     * returns it's immutable copy
     *
     * @param command is used as a source for a transfer creation. Must not be {@code null}
     * @return immutable copy of newly created transfer
     * @throws IllegalArgumentException if {@code command} is {@code null}
     */
    ImmutableTransfer deposit(DepositCommand command);
}
