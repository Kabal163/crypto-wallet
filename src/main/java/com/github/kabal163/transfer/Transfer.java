package com.github.kabal163.transfer;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transfer")
public class Transfer {

    /**
     * Transfer's unique identifier.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Timestamp of transfer creation in the wallet.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationTimestamp;

    /**
     * External timestamp which we receive with income transfer.
     * Specify a time, when transfer was sent to the wallet
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime transferTimestamp;

    /**
     * Amount of income transfer in Bitcoins
     */
    @Column(nullable = false, updatable = false)
    private BigDecimal amount;

    @PrePersist
    public void prePersist() {
        setCreationTimestamp(LocalDateTime.now());
    }
}
