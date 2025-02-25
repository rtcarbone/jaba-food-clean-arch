CREATE TABLE menu_items
(
    id                 UUID PRIMARY KEY,
    name               VARCHAR(255)   NOT NULL,
    description        TEXT           NOT NULL,
    price              DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    in_restaurant_only BOOLEAN        NOT NULL DEFAULT FALSE,
    image_path         VARCHAR(255),
    restaurant_id      UUID           NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
