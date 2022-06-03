CREATE TABLE orders
(
    id   SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    order_date timestamp
);

CREATE TABLE order_items
(
    id   SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    ski_id INT NOT NULL REFERENCES skis (id),
    amount INT NOT NULL
);

CREATE TABLE user_basket_item
(
    id   SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    ski_id INT NOT NULL REFERENCES skis (id),
    amount INT NOT NULL
);