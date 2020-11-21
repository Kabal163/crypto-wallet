package com.github.kabal163.service;

import com.github.kabal163.entity.immutable.ImmutableTransfer;

import java.util.List;

public interface TransferQueryService {

    List<ImmutableTransfer> getAggregated();
}
