package com.github.kabal163.wallet;

import com.github.kabal163.balance.BalanceFlushingService;
import com.github.kabal163.balance.LocalBalanceManager;
import com.github.kabal163.transfer.ImmutableTransfer;
import com.github.kabal163.transfer.Transfer;
import com.github.kabal163.transfer.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletCommandServiceImpl implements WalletCommandService {

    private final BalanceFlushingService balanceFlushingService;
    private final TransferRepository transferRepository;
    private final LocalBalanceManager localBalanceManager;

    @Override
    @Transactional
    public ImmutableTransfer deposit(DepositCommand command) {
        Assert.notNull(command, "DepositCommand must not be null!");

        Transfer transfer = transferRepository.save(command.getTransfer());
        log.info("Transfer has been created; amount: {}, id: {}", transfer.getAmount(), transfer.getId());

        balanceFlushingService.waitIfFlushIsRunning();
        localBalanceManager.incrementBalance(transfer.getAmount());

        return ImmutableTransfer.newInstance(transfer);
    }
}
