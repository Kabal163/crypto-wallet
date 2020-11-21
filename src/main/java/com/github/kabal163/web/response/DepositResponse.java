package com.github.kabal163.web.response;

import com.github.kabal163.transfer.ImmutableTransfer;
import lombok.Data;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representation of the newly created deposit transfer
 */
@Data
public class DepositResponse {

    private final UUID id;
    private final BigDecimal amount;

    public static DepositResponse newInstance(ImmutableTransfer source) {
        Assert.notNull(source, "ImmutableTransfer must not be null!");
        return new DepositResponse(source.getId(), source.getAmount());
    }
}
