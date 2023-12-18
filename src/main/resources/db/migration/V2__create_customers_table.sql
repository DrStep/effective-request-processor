CREATE TABLE IF NOT EXISTS customers_requests.customers
(
    id          SERIAL PRIMARY KEY,     -- automatically auto-incremented int
    name        VARCHAR(255) NOT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT true
);
