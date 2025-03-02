CREATE TABLE users
(
    id         UUID PRIMARY KEY             DEFAULT gen_random_uuid(),
    name       VARCHAR(255)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    login      VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    user_type  VARCHAR(50)         NOT NULL DEFAULT 'CUSTOMER',
    created_at TIMESTAMP                    DEFAULT NOW(),
    address_id UUID UNIQUE,
    CONSTRAINT fk_users_address FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE SET NULL
);
