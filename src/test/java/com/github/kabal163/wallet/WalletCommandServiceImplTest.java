package com.github.kabal163.wallet;

import com.github.kabal163.MotherObject;
import com.github.kabal163.balance.BalanceFlushingService;
import com.github.kabal163.balance.LocalBalanceManager;
import com.github.kabal163.transfer.ImmutableTransfer;
import com.github.kabal163.transfer.Transfer;
import com.github.kabal163.transfer.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WalletCommandServiceImplTest {

    //@formatter:off
    @Mock BalanceFlushingService balanceFlushingServiceMock;
    @Mock TransferRepository transferRepositoryMock;
    @Mock LocalBalanceManager localBalanceManagerMock;
    @Mock DepositCommand depositCommandMock;
    //@formatter:on

    WalletCommandServiceImpl walletCommandService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        defaultMocksBehaviour();

        walletCommandService = new WalletCommandServiceImpl(
                balanceFlushingServiceMock,
                transferRepositoryMock,
                localBalanceManagerMock);
    }

    @Test
    void givenNullCommand_whenCallDeposit_thenThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> walletCommandService.deposit(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");
    }

    @Test
    void whenCallDeposit_thenTransferRepository_saveMethodMustBeCalledOnce() {
        Transfer transfer = MotherObject.getTransfer().any().please();
        when(depositCommandMock.getTransfer())
                .thenReturn(transfer);

        walletCommandService.deposit(depositCommandMock);

        verify(transferRepositoryMock, times(1)).save(any(Transfer.class));
    }

    @Test
    void whenCallDeposit_thenTransferFromDepositCommandPassedToTransferRepository_saveMethod() {
        Transfer expected = MotherObject.getTransfer().any().please();
        ArgumentCaptor<Transfer> captor = ArgumentCaptor.forClass(Transfer.class);
        when(depositCommandMock.getTransfer())
                .thenReturn(expected);
        when(transferRepositoryMock.save(captor.capture()))
                .then(invocation -> invocation.getArgument(0));

        walletCommandService.deposit(depositCommandMock);

        Transfer actual = captor.getValue();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallDeposit_thenBalanceFlushingService_waitIfFlushIsRunningMethodIsCalledOnce() {
        Transfer transfer = MotherObject.getTransfer().any().please();
        when(depositCommandMock.getTransfer())
                .thenReturn(transfer);

        walletCommandService.deposit(depositCommandMock);

        verify(balanceFlushingServiceMock, times(1)).waitIfFlushIsRunning();
    }

    @Test
    void whenCallDeposit_thenLocalBalanceManager_incrementBalanceMethodIsCalledOnce() {
        Transfer transfer = MotherObject.getTransfer().any().please();
        when(depositCommandMock.getTransfer())
                .thenReturn(transfer);

        walletCommandService.deposit(depositCommandMock);

        verify(localBalanceManagerMock, times(1)).incrementBalance(any(BigDecimal.class));
    }

    @Test
    void whenCallDeposit_thenAmountFromTransferIsPassedToLocalBalanceManager_incrementBalanceMethod() {
        BigDecimal expected = new BigDecimal(111);
        ArgumentCaptor<BigDecimal> captor = ArgumentCaptor.forClass(BigDecimal.class);
        Transfer transfer = MotherObject.getTransfer()
                .any()
                .withAmount(expected)
                .please();
        when(depositCommandMock.getTransfer())
                .thenReturn(transfer);
        when(localBalanceManagerMock.incrementBalance(captor.capture()))
                .then(invocation -> invocation.getArgument(0));

        walletCommandService.deposit(depositCommandMock);

        BigDecimal actual = captor.getValue();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenCallDeposit_thenReturnsNotNullImmutableTransfer() {
        Transfer transfer = MotherObject.getTransfer().any().please();
        when(depositCommandMock.getTransfer())
                .thenReturn(transfer);

        ImmutableTransfer actual = walletCommandService.deposit(depositCommandMock);

        assertThat(actual).isNotNull();
    }

    @Test
    void whenCallDeposit_thenReturnsImmutableCopyOfTransferProvidedByDepositCommand() {
        final UUID id = UUID.randomUUID();
        final LocalDateTime creationTimestamp = LocalDateTime.now();
        Transfer transfer = MotherObject.getTransfer()
                .any()
                .withId(id)
                .withCreationTimestamp(creationTimestamp)
                .please();
        ImmutableTransfer expected = ImmutableTransfer.newInstance(transfer);
        when(depositCommandMock.getTransfer())
                .thenReturn(transfer);
        when(transferRepositoryMock.save(any(Transfer.class)))
                .then(invocation -> {
                    Transfer t  = invocation.getArgument(0);
                    t.setId(id);
                    t.setCreationTimestamp(creationTimestamp);
                    return t;
                });

        ImmutableTransfer actual = walletCommandService.deposit(depositCommandMock);

        assertThat(actual).isEqualTo(expected);
    }

    private void defaultMocksBehaviour() {
        when(transferRepositoryMock.save(any(Transfer.class)))
                .then(invocation -> invocation.getArgument(0));
        when(localBalanceManagerMock.incrementBalance(any(BigDecimal.class)))
                .then(invocation -> invocation.getArgument(0));
    }
}