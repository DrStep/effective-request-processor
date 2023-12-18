package org.vminds.requests.processor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vminds.requests.processor.dto.CustomerRequest;
import org.vminds.requests.processor.dto.HourlyStatsResponse;
import org.vminds.requests.processor.dto.StatisticsResponse;
import org.vminds.requests.processor.model.HourlyStats;
import org.vminds.requests.processor.repository.StatisticsRepository;

import java.sql.Timestamp;
import java.time.*;

@Service
public class StatsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public StatisticsResponse getStatsForCustomer(Integer customerId, ZonedDateTime dayToLookFor) {
        var dayToLookForTimestamp = Timestamp.from(dayToLookFor.toInstant());
        var dayToLookTillTimestamp = Timestamp.from(dayToLookFor.plusDays(1).toInstant());
        var recordsForTheDay = statisticsRepository.selectCustomerStatsForTheDay(customerId, dayToLookForTimestamp, dayToLookTillTimestamp);

        var totalPerDay = recordsForTheDay.stream()
                .map(r -> r.getRequestCount() + r.getInvalidCount())    // total per hour
                .reduce(0L, Long::sum);                         // total per day

        var preparedRecords = recordsForTheDay.stream().map(HourlyStatsResponse::new).toList();
        return new StatisticsResponse(customerId, dayToLookFor.toLocalDate().toString(), totalPerDay, preparedRecords);
    }

    public void saveRequest(CustomerRequest request, boolean isValid) {
        var entityForSave = fromCustomerRequest(request, isValid);
        statisticsRepository.upsertRecord(
                entityForSave.getCustomerId(),
                entityForSave.getTimeHour(),
                entityForSave.getRequestCount(),
                entityForSave.getInvalidCount());
    }

    private HourlyStats fromCustomerRequest(CustomerRequest request, boolean isValid) {
        return new HourlyStats(
                request.customerID(),
                roundToLowerHour(request.timestamp()),
                isValid ? 1 : 0L,
                !isValid ? 1 : 0L
        );
    }

    private Timestamp roundToLowerHour(Long timeMillis) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneOffset.UTC);
        LocalDateTime roundedDateTime = dateTime.withMinute(0).withSecond(0).withNano(0);
        return Timestamp.from(roundedDateTime.toInstant(ZoneOffset.UTC));
    }

}
