package com.github.kabal163.balance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalBalanceTest {

    LocalBalance localBalance;

    @BeforeEach
    void init() {
        localBalance = new LocalBalance();
    }

    @Test
    void givenNegativeDepositedAmount_whenCallDeposit_thenThrowsIllegalArgumentException() {
        BigDecimal depositedAmount = new BigDecimal(-1);
        assertThatThrownBy(() -> localBalance.deposit(depositedAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be negative");
    }

    @ParameterizedTest
    @MethodSource("getDepositsDataSet")
    void whenCallDeposit_thenReturnsSumOfCurrentAmountAndDeposited(BigDecimal initialAmount,
                                                                   BigDecimal depositedAmount,
                                                                   BigDecimal expectedResult) {
        localBalance = new LocalBalance(initialAmount);
        BigDecimal actualResult = localBalance.deposit(depositedAmount);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getResetDataSet")
    void whenCallReset_thenReturnsCurrentAmount(BigDecimal currentValue) {
        localBalance = new LocalBalance(currentValue);
        BigDecimal actual = localBalance.reset();

        assertThat(actual).isEqualTo(currentValue);
    }

    @ParameterizedTest
    @MethodSource("getDepositsDataSet")
    void whenCallDepositAndThenCallReset_thenResetReturnsSumOfInitialAmountAndDeposited(BigDecimal initialAmount,
                                                                                        BigDecimal depositedAmount,
                                                                                        BigDecimal expectedResult) {
        localBalance = new LocalBalance(initialAmount);
        localBalance.deposit(depositedAmount);
        BigDecimal actualResult = localBalance.reset();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void whenCallDepositMultipleTimes_thenAtTheEndWeGetSumOfAllDeposits() {
        final int expected = 500;
        BigDecimal result = null;
        for (int i = 0; i < expected; i++) {
            result = localBalance.deposit(new BigDecimal(1));
        }

        assertThat(result.intValue()).isEqualTo(expected);
    }

    @Test
    void whenCallDepositConcurrently_thenAtTheEndWeGetSumOfAllDeposits() throws InterruptedException {
        final int numberOfThreads = 100;
        final int numberOfDeposits = 200;
        final int expectedAmount = numberOfDeposits * numberOfThreads;

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < numberOfDeposits; j++) {
                    localBalance.deposit(new BigDecimal(1));
                }
                latch.countDown();
            });
        }
        latch.await();
        BigDecimal actual = localBalance.reset();

        assertThat(actual.intValue()).isEqualTo(expectedAmount);
    }

    static Stream<Arguments> getDepositsDataSet() {
        return Stream.of(
                Arguments.of(new BigDecimal(2), new BigDecimal(2), new BigDecimal(4)),
                Arguments.of(new BigDecimal(0), new BigDecimal(4), new BigDecimal(4)),
                Arguments.of(new BigDecimal(1), new BigDecimal(6), new BigDecimal(7)),
                Arguments.of(new BigDecimal(3), new BigDecimal(0), new BigDecimal(3)),
                Arguments.of(new BigDecimal(-2), new BigDecimal(0), new BigDecimal(-2)),
                Arguments.of(new BigDecimal(-2), new BigDecimal(4), new BigDecimal(2))
        );
    }

    static Stream<Arguments> getResetDataSet() {
        return Stream.of(
                Arguments.of(new BigDecimal(2)),
                Arguments.of(new BigDecimal(0)),
                Arguments.of(new BigDecimal(-2))
        );
    }
}