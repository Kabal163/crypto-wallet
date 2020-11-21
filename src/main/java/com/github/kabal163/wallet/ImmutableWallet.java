package com.github.kabal163.wallet;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents immutable copy of the {@link Wallet}
 * @see Wallet
 */
@Data
public class ImmutableWallet {

    private final UUID id;
    private final LocalDateTime creationTimestamp;
    private final LocalDateTime lastModifiedTimestamp;
    private final BigDecimal balance;
}
