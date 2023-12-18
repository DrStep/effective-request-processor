package org.vminds.requests.processor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ip_blacklist", schema = "customers_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpEntity {

    @Id
    private String ip;
}
