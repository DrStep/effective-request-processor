package org.vminds.requests.processor.dto;

import java.util.List;

/**
 *
 * @param statistics
 */
public record StatisticsResponse(
        Integer requestCustomerId,
        String requestDate,
        Long totalRequestsPerDay,
        List<HourlyStatsResponse> statistics
) {
}
