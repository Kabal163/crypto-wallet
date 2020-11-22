package com.github.kabal163.balance;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class manages {@link LocalBalance local balances}.
 * Each thread can work with it's own local balance via methods
 * which work with {@link #localBalance} variable.
 * The {@link #localBalances} variable stores links on the objects
 * from the {@link #localBalance} in order to count them and reset.
 * It may cause memory leaks due to additional links to ThreadLocal.
 * Need to think how to improve the solution.
 */
@Component
public class LocalBalanceManagerImpl implements LocalBalanceManager {

    private final ThreadLocal<LocalBalance> localBalance = ThreadLocal.withInitial(this::newInstance);
    private final List<LocalBalance> localBalances = new CopyOnWriteArrayList<>();

    /**
     * Balance must not be incremented while {@link #countAndReset()} is running,
     * so be sure that you don't invoke it until the {@link #countAndReset()}
     * is completed
     *
     * @param value amount which must be incremented to the current local balance
     * @return resulted local balance
     */
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
