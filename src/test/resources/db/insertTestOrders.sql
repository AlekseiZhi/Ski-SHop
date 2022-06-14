INSERT INTO ski (id, category, company, title, length, price)
VALUES (1, 'freeride', 'k2', 'marksmanTest1', 180, 35000),
       (2, 'freeride', 'k2', 'marksmanTest2', 150, 30000),
       (3, 'freeride', 'k2', 'marksmanTest3', 150, 30000);


INSERT INTO role (id, name)
VALUES (1, 'admin'),
       (2, 'user');

INSERT INTO users (id, email, full_name, password)
VALUES (1, 'admin@mail', 'admin', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi'),
       (2, 'user@mail', 'user', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi');

INSERT INTO user_basket_item (id, user_id, ski_id, amount)
VALUES (1, 1, 1, 1),
       (2, 1, 2, 2),
       (3, 2, 1, 1),
       (4, 2, 2, 2);

INSERT INTO orders (id, user_id, create_date)
VALUES (1, 1, '2022-06-06 18:38:49.200245'),
       (2, 1, '2022-06-06 18:38:49.200245');

INSERT INTO order_item (id, user_id, ski_id, amount, order_id)
VALUES (1, 1, 1, 3, 1),
       (2, 1, 2, 1, 1),
       (3, 1, 1, 1, 2),
       (4, 1, 2, 1, 2);