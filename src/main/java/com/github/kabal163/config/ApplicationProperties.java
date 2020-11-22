package com.github.kabal163.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@ConfigurationProperties("app")
public class ApplicationProperties {

    @NotNull
    private UUID walletId;
}
