package com.github.kabal163.balance;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.github.kabal163.balance.BalanceHistorySearchParams.AggregateBy.HOUR;
import static com.github.kabal163.balance.BalanceHistorySearchParams.AggregateBy.MINUTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BalanceHistorySearchParamsTest {

    @Test
    void givenStartDateTimeIsNull_whenCallBuild_thenThrowsIllegalArgumentException() {
        BalanceHistorySearchParams.Builder builder = BalanceHistorySearchParams.builder()
                .start(null)
                .end(LocalDateTime.now());

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");
    }

    @Test
    void givenEndDateTimeIsNull_whenCallBuild_thenThrowsIllegalArgumentException() {
        BalanceHistorySearchParams.Builder builder = BalanceHistorySearchParams.builder()
                .start(LocalDateTime.now())
                .end(null);

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");
    }

    @Test
    void whenCallBuild_thenReturnsNotNullBalanceHistoryInstance() {
        BalanceHistorySearchParams actual = BalanceHistorySearchParams.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .build();

        assertThat(actual).isNotNull();
    }

    @Test
    void givenNullAggregatedBy_whenCallBuild_thenReturnBalanceHistoryWithDefaultAggregatedBy() {
        BalanceHistorySearchParams.AggregateBy actual = BalanceHistorySearchParams.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .aggregateBy(null)
                .build()
                .getAggregateBy();

        assertThat(actual).isEqualTo(HOUR);
    }

    @Test
    void whenCallBuild_thenReturnBalanceHistoryWithSameStartDateTime() {
        LocalDateTime expected = LocalDateTime.of(1999, 1, 1, 0, 0, 0);
        LocalDateTime actual = BalanceHistorySearchParams.builder()
                .start(expected)
                .end(LocalDateTime.now())
                .aggregateBy(null)
                .build()
                .getStart();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallBuild_thenReturnBalanceHistoryWithSameEndDateTime() {
        LocalDateTime expected = LocalDateTime.of(1999, 1, 1, 0, 0, 0);
        LocalDateTime actual = BalanceHistorySearchParams.builder()
                .start(LocalDateTime.now())
                .end(expected)
                .aggregateBy(null)
                .build()
                .getEnd();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallBuild_thenReturnBalanceHistoryWithSameAggregatedBy() {
        BalanceHistorySearchParams.AggregateBy expected = MINUTE;
        BalanceHistorySearchParams.AggregateBy actual = BalanceHistorySearchParams.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .aggregateBy(expected)
                .build()
                .getAggregateBy();

        assertThat(actual).isEqualTo(expected);
    }
}