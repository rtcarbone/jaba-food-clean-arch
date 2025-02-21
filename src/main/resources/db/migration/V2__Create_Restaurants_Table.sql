CREATE TABLE restaurants
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    cuisine_type  VARCHAR(100) NOT NULL,
    opening_hours VARCHAR(255) NOT NULL,
    owner_id      UUID         NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);
