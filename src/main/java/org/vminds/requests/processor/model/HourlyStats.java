package org.vminds.requests.processor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Table(name = "hourly_stats", schema = "customers_requests", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "customer_id", "time_hour" })
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HourlyStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id", unique = true, nullable = false)
    private Integer customerId;

    @Column(name = "time_hour", nullable = false)
    private Timestamp timeHour;

    @Column(name = "request_count", nullable = false)
    private Long requestCount;

    @Column(name = "invalid_count", nullable = false)
    private Long invalidCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private Customer customer;

    public HourlyStats(Integer customerId, Timestamp timeHour, Long requestCount, Long invalidCount) {
        this.customerId = customerId;
        this.timeHour = timeHour;
        this.requestCount = requestCount;
        this.invalidCount = invalidCount;
    }
}
