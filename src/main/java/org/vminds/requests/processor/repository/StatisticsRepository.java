package org.vminds.requests.processor.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vminds.requests.processor.model.HourlyStats;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<HourlyStats, Long> {


    @Query(
            value = "SELECT * FROM customers_requests.hourly_stats  WHERE customer_id = ?1 AND time_hour >= ?2 AND time_hour < ?3 ORDER BY time_hour",
            nativeQuery = true
    )
    List<HourlyStats> selectCustomerStatsForTheDay(Integer customerId, Timestamp timeHourFrom, Timestamp timeHourTill);

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO customers_requests.hourly_stats (customer_id, time_hour, request_count, invalid_count) " +
                    "VALUES(?1, ?2, ?3, ?4) " +
                    "ON CONFLICT ON CONSTRAINT hourly_stats_customer_id_time_hour_key " +
                    "DO UPDATE SET request_count = EXCLUDED.request_count + ?3, invalid_count = EXCLUDED.invalid_count + ?4",
            nativeQuery = true
    )
    Integer upsertRecord(Integer customerId, Timestamp timeHour, Long requestCount, Long invalidCount);
}
