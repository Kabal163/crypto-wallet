package com.github.kabal163.balance;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class LocalBalanceManagerImpl implements LocalBalanceManager {

    private final ThreadLocal<LocalBalance> localBalance = ThreadLocal.withInitial(this::newInstance);
    private final List<LocalBalance> localBalances = new CopyOnWriteArrayList<>();

    @Override
    public BigDecimal incrementBalance(BigDecimal value) {
        LocalBalance balance = this.localBalance.get();
        balance.deposit(value);

        return balance.getAmount();
    }

    @Override
    public BigDecimal countAndReset() {
        return localBalances.stream()
                .map(this::getBalanceAndReset)
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    private BigDecimal getBalanceAndReset(LocalBalance balance) {
        BigDecimal amount = balance.getAmount();
        balance.reset();

        return amount;
    }

    private LocalBalance newInstance() {
        LocalBalance balance = new LocalBalance();
        localBalances.add(balance);

        return balance;
    }
}
