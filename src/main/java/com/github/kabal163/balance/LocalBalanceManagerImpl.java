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
        LocalBalance localBalance = this.localBalance.get();
        localBalance.deposit(value);

        return localBalance.getAmount();
    }

    @Override
    public BigDecimal countAndReset() {
        return localBalances.stream()
                .map(this::getBalanceAndReset)
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    private BigDecimal getBalanceAndReset(LocalBalance balance) {
        BigDecimal value = balance.getAmount();
        balance.reset();

        return value;
    }

    private LocalBalance newInstance() {
        LocalBalance localBalance = new LocalBalance();
        localBalances.add(localBalance);

        return localBalance;
    }
}
