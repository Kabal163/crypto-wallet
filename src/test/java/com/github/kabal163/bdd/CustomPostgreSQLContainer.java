package com.github.kabal163.bdd;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Set;

/**
 * @see "https://github.com/testcontainers/testcontainers-java/issues/3382"
 */
public class CustomPostgreSQLContainer extends PostgreSQLContainer<CustomPostgreSQLContainer> {

    public CustomPostgreSQLContainer(String dockerImageName) {
        super(dockerImageName);
    }

    @NotNull
    @Override
    protected Set<Integer> getLivenessCheckPorts() {
        return Set.of(getMappedPort(POSTGRESQL_PORT));
    }
}
