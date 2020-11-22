package com.github.kabal163.balance;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class manages {@link LocalBalance local balances}.
 * Each thread can work with it's own local balance via methods
 * which work with {@link #localBalance} variable.
 * The {@link #localBalances} variable stores links on the objects
 * from the {@link #localBalance} in order to count them and reset.
 * <p>NOTE!</p> Probably, it may cause memory leaks due to additional links to ThreadLocal.
 * Need to research.
 */
@Component
public class LocalBalanceManagerImpl implements LocalBalanceManager {

    private final ThreadLocal<LocalBalance> localBalance = ThreadLocal.withInitial(this::newInstance);
    private final List<LocalBalance> localBalances = new CopyOnWriteArrayList<>();

    @Override
    public BigDecimal incrementBalance(BigDecimal value) {
        Assert.notNull(value, "value must not be null!");
        LocalBalance balance = this.localBalance.get();
        return balance.deposit(value);
    }

    @Override
    public BigDecimal countAndReset() {
        return localBalances.stream()
                .map(LocalBalance::reset)
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    private LocalBalance newInstance() {
        LocalBalance balance = new LocalBalance();
        localBalances.add(balance);

        return balance;
    }
}
