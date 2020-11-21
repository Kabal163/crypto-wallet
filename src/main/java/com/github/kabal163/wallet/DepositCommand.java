package com.github.kabal163.wallet;

import com.github.kabal163.transfer.Transfer;
import lombok.Data;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a command to deposit money to a wallet
 * Contains information about income transfer
 */
@Data
public class DepositCommand {

    private final LocalDateTime transferTimestamp;
    private final BigDecimal amount;

    public Transfer getTransfer() {
        Transfer transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setTransferTimestamp(transferTimestamp);

        return transfer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private LocalDateTime transferTimestamp;
        private BigDecimal amount;

        public Builder transferTimestamp(LocalDateTime transferTimestamp) {
            this.transferTimestamp = transferTimestamp;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public DepositCommand build() {
            Assert.notNull(transferTimestamp, "dateTime must not be null!");
            Assert.notNull(amount, "amount must not be null!");

            return new DepositCommand(transferTimestamp, amount);
        }
    }
}
