package com.github.kabal163;

import com.github.kabal163.transfer.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class MotherObject {

    public static TransferBuilder getTransfer() {
        return new TransferBuilder();
    }

    public static final class TransferBuilder {
        private final Transfer transfer = new Transfer();

        private static final LocalDateTime past = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

        public TransferBuilder any() {
            withAnyId();
            withAnyAmount();
            withAnyCreationTimestamp();
            withAnyTransferTimestamp();

            return this;
        }

        public Transfer please() {
            return this.transfer;
        }

        public TransferBuilder withId(UUID id) {
            transfer.setId(id);
            return this;
        }

        public TransferBuilder withNullId() {
            transfer.setId(null);
            return this;
        }

        public TransferBuilder withAnyId() {
            transfer.setId(UUID.randomUUID());
            return this;
        }

        public TransferBuilder withCreationTimestamp(LocalDateTime creationTimestamp) {
            transfer.setCreationTimestamp(creationTimestamp);
            return this;
        }

        public TransferBuilder withNullCreationTimestamp() {
            transfer.setCreationTimestamp(null);
            return this;
        }

        public TransferBuilder withAnyCreationTimestamp() {
            transfer.setCreationTimestamp(past);
            return this;
        }

        public TransferBuilder withTransferTimestamp(LocalDateTime transferTimestamp) {
            transfer.setTransferTimestamp(transferTimestamp);
            return this;
        }

        public TransferBuilder withNullTransferTimestamp() {
            transfer.setTransferTimestamp(null);
            return this;
        }

        public TransferBuilder withAnyTransferTimestamp() {
            transfer.setTransferTimestamp(past);
            return this;
        }

        public TransferBuilder withAmount(BigDecimal amount) {
            transfer.setAmount(amount);
            return this;
        }

        public TransferBuilder withNullAmount() {
            transfer.setAmount(null);
            return this;
        }

        public TransferBuilder withAnyAmount() {
            transfer.setAmount(new BigDecimal(10));
            return this;
        }
    }
}
