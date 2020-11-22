/**
 * Package provides interfaces and classes to work with
 * wallet and it's balance.
 * Wallet balance is eventually consistent, thus there is
 * an in-memory balance buffer which must be flushed from time to time.
 * Also there are some util classes which helps to show
 * balance history
 * @see com.github.kabal163.balance.BalanceFlushingService to flush
 * buffered balance to a database.
 * @see com.github.kabal163.balance.BalanceQueryService to show balance
 * history
 */
@NonNullApi
package com.github.kabal163.balance;

import org.springframework.lang.NonNullApi;