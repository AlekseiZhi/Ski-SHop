CREATE TABLE order_item
(
    id   SERIAL PRIMARY KEY,
    ski_id INT NOT NULL REFERENCES skis (id),
    --order_id INT NOT NULL REFERENCES orderSki (id),
    amount INT NOT NULL
);

CREATE TABLE order_ski
(
    id   SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    order_date date ,
    order_item INT NOT NULL REFERENCES order_item (id)
);

CREATE TABLE user_basket_item
(
    id   SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    ski_id INT NOT NULL REFERENCES skis (id),
    amount INT NOT NULL
);

ALTER TABLE order_item ADD order_id INT  REFERENCES order_ski (id);