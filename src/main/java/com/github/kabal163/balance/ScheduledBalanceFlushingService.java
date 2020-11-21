package com.github.kabal163.balance;

import com.github.kabal163.wallet.Wallet;
import com.github.kabal163.wallet.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class ScheduledBalanceFlushingService implements BalanceFlushingService {

    private final WalletRepository walletRepository;
    private final LocalBalanceManager localBalanceManager;
    private final ReentrantLock lock;
    private final Condition condition;

    private volatile boolean flushRunning = false;

    public ScheduledBalanceFlushingService(WalletRepository walletRepository,
                                           LocalBalanceManager localBalanceManager) {
        this.walletRepository = walletRepository;
        this.localBalanceManager = localBalanceManager;
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
            wallet.deposit(localBalanceManager.countAndReset());
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
                log.error("Error while waiting a lock of balance flushing!", ex);
                throw new BalanceFlushTimeoutException("Error while waiting a lock of balance flushing!", ex);
            }
        }
    }
}
