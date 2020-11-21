package com.github.kabal163.wallet;

import com.github.kabal163.balance.BalanceFlushingService;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Not thead safe
 */
@Data
@Entity
@Audited
@Table(name = "wallet")
public class Wallet {

    /**
     * Wallet's unique identifier
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Timestamp of the wallet creation
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationTimestamp;

    /**
     * Timestamp of the last wallet modification
     */
    @Column(nullable = false)
    private LocalDateTime lastModifiedTimestamp;

    /**
     * Current wallet's balance
     * It may be not up to date between {@link BalanceFlushingService#flush() flush operations}
     */
    private BigDecimal balance;

    /**
     * Add the specified {@code value} sum to the current {@link #balance}
     *
     * @param value sum which must be added to the current balance
     * @return result balance
     */
    public BigDecimal deposit(BigDecimal value) {
        balance = balance.add(value);
        return balance;
    }

    @PrePersist
    public void prePersist() {
        setCreationTimestamp(LocalDateTime.now());
        setLastModifiedTimestamp(getCreationTimestamp());
    }

    @PreUpdate
    public void preUpdate() {
        setLastModifiedTimestamp(LocalDateTime.now());
    }
}
