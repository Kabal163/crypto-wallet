package com.github.kabal163.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<BalanceSnapshot> getAggregatedHistory(BalanceHistorySearchParams params) {
        Assert.notNull(params, "BalanceHistorySearchParams must not be null!");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("start", params.getStart());
        parameters.addValue("end", params.getEnd());
        parameters.addValue("aggregateBy", params.getAggregateBy().getValue());

        return jdbcTemplate.query(
                AGGREGATED_HISTORY,
                parameters,
                getRowMapper());
    }

    private RowMapper<BalanceSnapshot> getRowMapper() {
        return (rs, rowNum) -> BalanceSnapshot.builder()
                .datetime(toLocalDateTimeOrNull(rs.getTimestamp("datetime")))
                .amount(rs.getBigDecimal("amount"))
                .build();
    }

    @Nullable
    private LocalDateTime toLocalDateTimeOrNull(@Nullable Timestamp timestamp) {
        return ofNullable(timestamp)
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }

    private static final String AGGREGATED_HISTORY = "" +
            "SELECT datetime,\n" +
            "       MAX(balance) amount\n" +
            "  FROM (\n" +
            "           SELECT date_trunc(:aggregateBy, last_modified_timestamp) as datetime,\n" +
            "                  balance\n" +
            "           FROM wallet_snapshot\n" +
            "           WHERE date_trunc(:aggregateBy, last_modified_timestamp) BETWEEN :start AND :end\n" +
            "       ) trunc\n" +
            "GROUP BY datetime\n" +
            "ORDER BY datetime";
}
