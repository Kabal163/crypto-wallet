package com.github.kabal163.balance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalBalanceManagerImplTest {

    LocalBalanceManagerImpl localBalanceManager;

    @BeforeEach
    void init() {
        localBalanceManager = new LocalBalanceManagerImpl();
    }

    @Test
    void givenIncrementedValueIsNull_whenCallIncrementBalance_thenThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> localBalanceManager.incrementBalance(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");
    }

    @Test
    void whenCallIncrementBalance_thenReturnsNotNull() {
        BigDecimal actual = localBalanceManager.incrementBalance(new BigDecimal(2));

        assertThat(actual).isNotNull();
    }

    @Test
    void whenCallCountAndReset_thenReturnsNotNull() {
        BigDecimal actual = localBalanceManager.countAndReset();

        assertThat(actual).isNotNull();
    }

    @Test
    void givenIncrementedValueIsZero_whenCallIncrementBalance_thenReturnsSameValue() {
        final BigDecimal expected = new BigDecimal(0);
        BigDecimal actual = localBalanceManager.incrementBalance(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallIncrementBalanceMultipleTimes_thenLastReturnedResultMustBeSumOfAllIncrementedValues() {
        final int times = 100;
        final BigDecimal value = BigDecimal.valueOf(3.333);
        final BigDecimal expected = value.multiply(new BigDecimal(times));

        BigDecimal actual = null;
        for (int i = 0; i < times; i++) {
            actual = localBalanceManager.incrementBalance(value);
        }

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallIncrementBalanceMultipleTimes_andThenCallCountAndReset_thenReturnsSumOfAllIncrementedValues() {
        final int times = 100;
        final BigDecimal value = BigDecimal.valueOf(3.123);
        final BigDecimal expected = value.multiply(new BigDecimal(times));

        for (int i = 0; i < times; i++) {
            localBalanceManager.incrementBalance(value);
        }
        BigDecimal actual = localBalanceManager.countAndReset();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallIncrementBalanceThenCountAndResetSequentially_thenLastIncrementBalanceReturnsTheIncrementedValue() {
        final BigDecimal expected = BigDecimal.valueOf(2.213);

        localBalanceManager.incrementBalance(BigDecimal.valueOf(12.125));
        localBalanceManager.countAndReset();
        localBalanceManager.incrementBalance(BigDecimal.valueOf(5.156));
        localBalanceManager.countAndReset();
        BigDecimal actual = localBalanceManager.incrementBalance(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallIncrementBalanceThenCountAndResetSequentially_thenLastCountAndResetReturnsTheLastIncrementedValue() {
        final BigDecimal expected = BigDecimal.valueOf(2.213);

        localBalanceManager.incrementBalance(BigDecimal.valueOf(12.125));
        localBalanceManager.countAndReset();
        localBalanceManager.incrementBalance(BigDecimal.valueOf(5.156));
        localBalanceManager.countAndReset();
        localBalanceManager.incrementBalance(expected);
        BigDecimal actual = localBalanceManager.countAndReset();

        assertThat(actual).isEqualTo(expected);
    }
}