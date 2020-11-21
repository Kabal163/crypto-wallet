package com.github.kabal163.balance;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BalanceContextProviderImpl implements BalanceContextProvider {

    private final List<BalanceContext> balanceContexts = new CopyOnWriteArrayList<>();

    @Override
    public BalanceContext newInstance() {
        BalanceContext balanceContext = new BalanceContext();
        balanceContexts.add(balanceContext);

        return balanceContext;
    }

    @Override
    public BigDecimal countAndReset() {
        return balanceContexts.stream()
                .map(this::getBalanceAndReset)
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    private BigDecimal getBalanceAndReset(BalanceContext ctx) {
        BigDecimal balance = ctx.getBalance();
        ctx.reset();

        return balance;
    }
}
