package org.vminds.requests.processor.dto;

import org.vminds.requests.processor.model.HourlyStats;

import java.sql.Timestamp;

public record HourlyStatsResponse(
        Integer customerId,
        Timestamp timeHour,
        Long requestCount,
        Long invalidCount
) {
    public HourlyStatsResponse(HourlyStats stats) {
        this(stats.getCustomerId(), stats.getTimeHour(), stats.getRequestCount(), stats.getInvalidCount());
    }
}
