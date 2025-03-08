CREATE TABLE restaurants
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(255) NOT NULL,
    cuisine_type VARCHAR(50)  NOT NULL CHECK (cuisine_type IN ('PIZZERIA', 'BURGER', 'ITALIAN', 'JAPANESE')),
    opening_time TIME         NOT NULL,
    closing_time TIME         NOT NULL,
    owner_id     UUID         NOT NULL,
    address_id   UUID         NOT NULL,
    created_at   TIMESTAMP        DEFAULT NOW(),
    FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE CASCADE,
    CHECK (opening_time < closing_time) -- Garante que a abertura é antes do fechamento
);

