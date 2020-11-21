package com.github.kabal163.entity.immutable;

import com.github.kabal163.entity.Transfer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class ImmutableTransfer {

    private final UUID id;
    private final LocalDateTime creationTimestamp;
    private final LocalDateTime transferTimestamp;
    private final BigDecimal amount;

    public static ImmutableTransfer newInstance(Transfer source) {
        Assert.notNull(source, "Source transfer must not be null!");
        return ImmutableTransfer.builder()
                .id(source.getId())
                .creationTimestamp(source.getCreationTimestamp())
                .transferTimestamp(source.getTransferTimestamp())
                .amount(source.getAmount())
                .build();
    }
}
