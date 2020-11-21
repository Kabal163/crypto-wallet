package com.github.kabal163.balance;

public interface BalanceFlushingService {

    void flush();

    void waitIfFlushIsRunning();
}
