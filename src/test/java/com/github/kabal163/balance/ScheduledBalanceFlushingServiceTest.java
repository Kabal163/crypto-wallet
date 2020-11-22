package com.github.kabal163.balance;

import com.github.kabal163.config.ApplicationProperties;
import com.github.kabal163.wallet.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScheduledBalanceFlushingServiceTest {

    //@formatter:off
    @Mock ApplicationProperties properties;
    @Mock WalletRepository walletRepository;
    @Mock LocalBalanceManager localBalanceManager;
    //@formatter:off

    ScheduledBalanceFlushingService balanceFlushingService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        balanceFlushingService = new ScheduledBalanceFlushingService(
                properties,
                walletRepository,
                localBalanceManager);
    }
}