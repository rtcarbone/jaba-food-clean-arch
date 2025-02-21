CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    username   VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    user_type  VARCHAR(50)         NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);
