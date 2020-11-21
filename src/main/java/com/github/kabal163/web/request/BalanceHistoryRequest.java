package com.github.kabal163.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

/**
 * Request describes a time range of wallet's balance history
 */
@Data
public class BalanceHistoryRequest {

    /**
     * Start date time of the interesting balance history range
     */
    @JsonProperty("startDatetime")
    @Past(message = "{\"field\":\"startDatetime\", \"message\":\"Must be date time from past\"}")
    @NotNull(message = "{\"field\":\"startDatetime\", \"message\":\"Must not be null\"}")
    private final LocalDateTime start;

    /**
     * End date time of the interesting balance history range
     */
    @JsonProperty("endDatetime")
    @Past(message = "{\"field\":\"endDatetime\", \"message\":\"Must be date time from past\"}")
    @Nullable
    private final LocalDateTime end;
}
