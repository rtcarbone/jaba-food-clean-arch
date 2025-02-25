CREATE TABLE addresses
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    street     VARCHAR(255) NOT NULL,
    city       VARCHAR(255) NOT NULL,
    state      VARCHAR(100) NOT NULL,
    zip_code   VARCHAR(20)  NOT NULL,
    country    VARCHAR(100) NOT NULL,
    created_at TIMESTAMP        DEFAULT NOW()
);