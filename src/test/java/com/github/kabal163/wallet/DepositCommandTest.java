package com.github.kabal163.wallet;

import com.github.kabal163.transfer.Transfer;
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
        LocalDateTime expected = LocalDateTime.of(1999, 1, 1, 0, 0, 0);
        LocalDateTime actual = DepositCommand.builder()
                .amount(new BigDecimal(1))
                .transferTimestamp(expected)
                .build()
                .getTransferTimestamp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallGetTransfer_thenReturnsNotNullTransfer() {
        Transfer actual = DepositCommand.builder()
                .amount(new BigDecimal(1))
                .transferTimestamp(LocalDateTime.now())
                .build()
                .getTransfer();

        assertThat(actual).isNotNull();
    }

    @Test
    void givenAmount_whenCallGetTransfer_thenReturnsTransferWithSameAmount() {
        BigDecimal expected = new BigDecimal(111);
        BigDecimal actual = DepositCommand.builder()
                .amount(expected)
                .transferTimestamp(LocalDateTime.now())
                .build()
                .getTransfer()
                .getAmount();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenTransferTimestamp_whenCallGetTransfer_thenReturnsTransferWithSameTransferTimestamp() {
        LocalDateTime expected = LocalDateTime.of(1999, 1, 1, 0, 0, 0);
        LocalDateTime actual = DepositCommand.builder()
                .amount(new BigDecimal(0))
                .transferTimestamp(expected)
                .build()
                .getTransfer()
                .getTransferTimestamp();

        assertThat(actual).isEqualTo(expected);
    }
}