package com.github.kabal163.wallet;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DepositCommandTest {

    @Test
    void givenAmountIsNull_whenCallBuild_thenThrowsIllegalArgumentException() {
        DepositCommand.Builder builder = DepositCommand.builder()
                .amount(null)
                .transferTimestamp(LocalDateTime.now());

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");

    }

    @Test
    void givenTransferTimestampIsNull_whenCallBuild_thenThrowsIllegalArgumentException() {
        DepositCommand.Builder builder = DepositCommand.builder()
                .amount(new BigDecimal(1))
                .transferTimestamp(null);

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");

    }

    @Test
    void whenCallBuild_thenReturnsNotNullDepositCommand() {
        DepositCommand actual = DepositCommand.builder()
                .amount(new BigDecimal(1))
                .transferTimestamp(LocalDateTime.now())
                .build();

        assertThat(actual).isNotNull();
    }

    @Test
    void whenCallBuild_thenReturnDepositCommandWithSpecifiedAmount() {
        BigDecimal expected = new BigDecimal(111);
        BigDecimal actual = DepositCommand.builder()
                .amount(expected)
                .transferTimestamp(LocalDateTime.now())
                .build()
                .getAmount();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallBuild_thenReturnDepositCommandWithSpecifiedTransferTimestamp() {
        LocalDateTime expected = LocalDateTime.now();
        LocalDateTime actual = DepositCommand.builder()
                .amount(new BigDecimal(1))
                .transferTimestamp(expected)
                .build()
                .getTransferTimestamp();

        assertThat(actual).isEqualTo(expected);
    }
}