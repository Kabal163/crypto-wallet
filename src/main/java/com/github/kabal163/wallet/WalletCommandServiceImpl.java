package com.github.kabal163.wallet;

import com.github.kabal163.balance.BalanceContext;
import com.github.kabal163.balance.BalanceContextProvider;
import com.github.kabal163.balance.BalanceFlushingService;
import com.github.kabal163.transfer.Transfer;
import com.github.kabal163.transfer.ImmutableTransfer;
import com.github.kabal163.transfer.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletCommandServiceImpl implements WalletCommandService {

    private final BalanceFlushingService balanceFlushingService;
    private final TransferRepository transferRepository;
    private final BalanceContextProvider balanceContextProvider;

    private ThreadLocal<BalanceContext> concurrentBalance;

    @PostConstruct
    public void init() {
        concurrentBalance = ThreadLocal.withInitial(balanceContextProvider::newInstance);
    }

    @Override
    @Transactional
    public ImmutableTransfer deposit(DepositCommand command) {
        Assert.notNull(command, "DepositCommand must not be null!");

        Transfer transfer = command.getTransfer();
        Transfer result = transferRepository.save(transfer);
        log.info("Transfer has been created; amount: {}, id: {}", result.getAmount(), result.getId());

        balanceFlushingService.waitIfFlushIsRunning();
        BalanceContext threadBalance = concurrentBalance.get();
        threadBalance.deposit(result.getAmount());

        return ImmutableTransfer.newInstance(result);
    }
}
