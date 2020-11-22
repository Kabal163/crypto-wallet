package com.github.kabal163.balance;

import com.github.kabal163.config.ApplicationProperties;
import com.github.kabal163.wallet.Wallet;
import com.github.kabal163.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledBalanceFlushingService implements BalanceFlushingService {

    private final ApplicationProperties properties;
    private final WalletRepository walletRepository;
    private final LocalBalanceManager localBalanceManager;

    @Override
    @Scheduled(cron = "*/5 * * * * *")
    public void flush() {
        log.trace("Flushing wallet's balance...");

        Wallet wallet = walletRepository
                .findById(properties.getWalletId())
                .orElseThrow();
        wallet.deposit(localBalanceManager.countAndReset());
        walletRepository.save(wallet);

        log.debug("Wallet's balance has been updated; The current balance: {}", wallet.getBalance());
    }
}