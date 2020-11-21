package com.github.kabal163.wallet;

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

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationTimestamp;

    @Column(nullable = false)
    private LocalDateTime lastModifiedTimestamp;

    private BigDecimal balance;

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
