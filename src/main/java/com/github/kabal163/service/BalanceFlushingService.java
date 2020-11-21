package com.github.kabal163.service;

public interface BalanceFlushingService {

    void flush();

    void waitIfFlushIsRunning();
}
