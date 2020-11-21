package com.github.kabal163.entity.immutable;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ImmutableWallet {

    private final UUID id;
    private final LocalDateTime creationTimestamp;
    private final LocalDateTime lastModifiedTimestamp;
    private final BigDecimal balance;
}
