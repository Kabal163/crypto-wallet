package com.github.kabal163.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request for a deposit. Contains all necessary information
 * to crate a new transfer in the system.
 */
@Data
public class DepositRequest {

    /**
     * Timestamp of the transfer in the system which initiates the operation
     */
    @Past(message = "{\"field\":\"datetime\",\"message\":\"Transfer's timestamp must be from past\"}")
    @NotNull(message = "{\"field\":\"datetime\",\"message\":\"The transfer's timestamp must not be null\"}")
    @JsonProperty("datetime")
    private final LocalDateTime dateTime;

    /**
     * Amount of income in Bitcoins
     */
    @Positive(message = "{\"field\":\"amount\",\"message\":\"Transfer's amount must be positive\"}")
    @NotNull(message = "{\"field\":\"amount\",\"message\":\"The transfer's amount must not be null\"}")
    private final BigDecimal amount;
}
