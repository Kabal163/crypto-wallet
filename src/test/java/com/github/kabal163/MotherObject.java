package com.github.kabal163;

import com.github.kabal163.balance.BalanceHistorySearchParams;
import com.github.kabal163.transfer.Transfer;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.kabal163.balance.BalanceHistorySearchParams.AggregateBy.MINUTE;

public class MotherObject {

    private static final LocalDateTime past = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

    public static TransferBuilder getTransfer() {
        return new TransferBuilder();
    }

    public static BalanceHistorySearchParamsBuilder getBalanceHistory() {
        return new BalanceHistorySearchParamsBuilder();
    }

    public static final class TransferBuilder {
        private final Transfer transfer = new Transfer();

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

    public static final class BalanceHistorySearchParamsBuilder {
        BalanceHistorySearchParams.Builder builder = BalanceHistorySearchParams.builder();

        public BalanceHistorySearchParamsBuilder any() {
            withAnyStart();
            withAnyEnd();
            withAnyAggregatedBy();

            return this;
        }

        public BalanceHistorySearchParams please() {
            return builder.build();
        }

        public BalanceHistorySearchParamsBuilder withStart(LocalDateTime start) {
            builder.start(start);
            return this;
        }

        public BalanceHistorySearchParamsBuilder withAnyStart() {
            builder.start(past);
            return this;
        }

        public BalanceHistorySearchParamsBuilder withEnd(LocalDateTime end) {
            builder.end(end);
            return this;
        }

        public BalanceHistorySearchParamsBuilder withAnyEnd() {
            builder.end(past);
            return this;
        }

        public BalanceHistorySearchParamsBuilder withAggregatedBy(BalanceHistorySearchParams.AggregateBy aggregatedBy) {
            builder.aggregateBy(aggregatedBy);
            return this;
        }

        public BalanceHistorySearchParamsBuilder withAnyAggregatedBy() {
            builder.aggregateBy(MINUTE);
            return this;
        }
    }
}
