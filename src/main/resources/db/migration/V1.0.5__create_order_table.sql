CREATE TABLE orders
(
    id          SERIAL PRIMARY KEY,
    user_id     INT       NOT NULL REFERENCES users (id),
    create_date timestamp NOT NULL
);

CREATE TABLE order_item
(
    id       SERIAL PRIMARY KEY,
    user_id  INT NOT NULL REFERENCES users (id),
    ski_id   INT NOT NULL REFERENCES ski (id),
    amount   INT NOT NULL,
    order_id INT NOT NULL REFERENCES orders (id)
);

CREATE TABLE user_basket_item
(
    id      SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    ski_id  INT NOT NULL REFERENCES ski (id),
    amount  INT NOT NULL
);