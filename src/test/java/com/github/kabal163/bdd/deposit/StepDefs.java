package com.github.kabal163.bdd.deposit;

import com.github.kabal163.bdd.CucumberTestContext;
import com.github.kabal163.bdd.HttpClient;
import com.github.kabal163.bdd.SpringIntegrationTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>Uses Java Lambda style step definitions so that we don't need to worry
 * about method naming for each step definition</p>
 */
@CucumberContextConfiguration
public class StepDefs extends SpringIntegrationTest implements En {

    private final CucumberTestContext CONTEXT = CucumberTestContext.CONTEXT;

    public StepDefs(HttpClient httpClient) {

        Given("user wants to create deposit with the following parameters", (DataTable transferDt) -> {
            List<Transfer> transfer = transferDt.asList(Transfer.class);
            CONTEXT.setPayload(transfer.get(0));
        });

        When("user calls API", () -> {
            String path = "/api/v1/public/wallet/deposit";
            httpClient.executePost(path);
        });

        DataTableType((Map<String, String> row) -> Transfer.builder()
                .datetime(row.get("datetime") == null ? null : LocalDateTime.parse(row.get("datetime")))
                .amount(row.get("amount") == null ? null : new BigDecimal(row.get("amount")))
                .build());

        DataTableType((Map<String, String> row) -> DepositResponse.builder()
                .id(row.get("id") == null ? null : UUID.fromString(row.get("datetime")))
                .amount(row.get("amount") == null ? null : new BigDecimal(row.get("amount")))
                .build());
    }

    @Data
    @Builder
    public static final class Transfer {
        private final LocalDateTime datetime;
        private final BigDecimal amount;
    }

    @Data
    @Builder
    public static final class DepositResponse {
        private final UUID id;
        private final BigDecimal amount;
    }
}
