CREATE TABLE IF NOT EXISTS customers_requests.hourly_stats
(
    id            SERIAL PRIMARY KEY,
    customer_id   INT NOT NULL,
    time_hour     timestamp,
    request_count BIGINT NOT NULL DEFAULT 0 CHECK (request_count >= 0),
    invalid_count BIGINT NOT NULL DEFAULT 0 CHECK (invalid_count >= 0),
    UNIQUE(customer_id, time_hour),
    FOREIGN KEY (customer_id) REFERENCES customers_requests.customers(id) ON DELETE CASCADE ON UPDATE NO ACTION
);
CREATE INDEX customer_id_idx ON customers_requests.hourly_stats (customer_id);