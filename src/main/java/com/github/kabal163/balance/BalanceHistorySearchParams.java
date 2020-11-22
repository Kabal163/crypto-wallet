package com.github.kabal163.balance;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

import static com.github.kabal163.balance.BalanceHistorySearchParams.AggregateBy.HOUR;

@Data
public class BalanceHistorySearchParams {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final AggregateBy aggregateBy;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private LocalDateTime start;
        private LocalDateTime end;
        private AggregateBy aggregateBy;

        public Builder start(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public Builder end(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public Builder aggregateBy(@Nullable AggregateBy aggregateBy) {
            this.aggregateBy = aggregateBy;
            return this;
        }

        public BalanceHistorySearchParams build() {
            Assert.notNull(start, "Start date time must not be null!");
            Assert.notNull(end, "End date time must not be null!");
            aggregateBy = aggregateBy == null ? HOUR : aggregateBy;

            return new BalanceHistorySearchParams(
                    start,
                    end,
                    aggregateBy);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum AggregateBy {
        MINUTE("minute"),
        HOUR("hour"),
        DAY("day");

        private final String value;
    }
}
