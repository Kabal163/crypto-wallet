package com.github.kabal163.balance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceQueryServiceImpl implements BalanceQueryService {

    private final BalanceRepository repository;

    @Override
    public List<BalanceSnapshot> getBalanceHistory(LocalDateTime start, LocalDateTime end) {
        return repository.getAggregatedHistory(start, end);
    }
}
