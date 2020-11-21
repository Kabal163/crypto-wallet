package com.github.kabal163.transfer;

import com.github.kabal163.MotherObject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImmutableTransferTest {

    @Test
    void givenNullSourceTransfer_whenCallNewInstance_thenThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> ImmutableTransfer.newInstance(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");
    }

    @Test
    void whenCallNewInstance_thenMustNotReturnNull() {
        ImmutableTransfer actual = ImmutableTransfer.newInstance(
                MotherObject.getTransfer()
                        .any()
                        .please()
        );

        assertThat(actual).isNotNull();
    }

    @Test
    void givenSourceTransferWithId_whenCallNewInstance_thenReturnImmutableTransferWithSameId() {
        UUID expected = UUID.randomUUID();
        Transfer transfer = MotherObject.getTransfer()
                .any()
                .withId(expected)
                .please();
        UUID actual = ImmutableTransfer.newInstance(transfer).getId();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSourceTransferWithCreationTimestamp_whenCallNewInstance_thenReturnImmutableTransferWithSameCreationTimestamp() {
        LocalDateTime expected = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        Transfer transfer = MotherObject.getTransfer()
                .any()
                .withCreationTimestamp(expected)
                .please();
        LocalDateTime actual = ImmutableTransfer.newInstance(transfer).getCreationTimestamp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSourceTransferWithTransferTimestamp_whenCallNewInstance_thenReturnImmutableTransferWithSameTransferTimestamp() {
        LocalDateTime expected = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        Transfer transfer = MotherObject
                .getTransfer()
                .any()
                .withTransferTimestamp(expected).please();
        LocalDateTime actual = ImmutableTransfer.newInstance(transfer).getTransferTimestamp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSourceTransferWithAmount_whenCallNewInstance_thenReturnImmutableTransferWithSameAmount() {
        BigDecimal expected = new BigDecimal(1);
        Transfer transfer = MotherObject.getTransfer()
                .any()
                .withAmount(expected)
                .please();
        BigDecimal actual = ImmutableTransfer.newInstance(transfer).getAmount();

        assertThat(actual).isEqualTo(expected);
    }
}