package com.github.kabal163.service;

import com.github.kabal163.balance.BalanceContextProvider;
import com.github.kabal163.entity.Wallet;
import com.github.kabal163.exception.DepositWaitTimeoutException;
import com.github.kabal163.persistance.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class ScheduledBalanceFlushingService implements BalanceFlushingService {

    private final WalletRepository walletRepository;
    private final BalanceContextProvider balanceContextProvider;
    private final ReentrantLock lock;
    private final Condition condition;

    private volatile boolean flushRunning = false;

    public ScheduledBalanceFlushingService(WalletRepository walletRepository,
                                           BalanceContextProvider balanceContextProvider) {
        this.walletRepository = walletRepository;
        this.balanceContextProvider = balanceContextProvider;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    @Override
    @Scheduled(cron = "*/5 * * * * *")
    public void flush() {
        lock.lock();
        flushRunning = true;
        try {
            log.trace("Flushing wallet's balance...");
            Wallet wallet = walletRepository.findById(UUID.fromString("b1116a97-1e7c-484b-a111-89cc718c7772")).orElseThrow();
            wallet.deposit(balanceContextProvider.countAndReset());
            walletRepository.save(wallet);
            log.debug("Wallet's balance has been updated; The current balance: {}", wallet.getBalance());
        } finally {
            flushRunning = false;
            condition.signalAll();
            lock.unlock();
        }
    }

    @Override
    public void waitIfFlushIsRunning() {
        while (flushRunning) {
            try {
                condition.await();
            } catch (InterruptedException ex) {
                log.error("Error while waiting a lock of balance flushing! Transfer will be rolled backed!", ex);
                throw new DepositWaitTimeoutException("Error while waiting a lock of balance flushing!", ex);
            }
        }
    }
}
