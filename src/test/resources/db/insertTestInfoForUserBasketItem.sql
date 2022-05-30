INSERT INTO skis (id, category, company, title, length, price)
VALUES (1, 'freeride', 'k2', 'marksmanTest1', 180, 35000);

INSERT INTO skis (id, category, company, title, length, price)
VALUES (2, 'freeride', 'k2', 'marksmanTest2', 150, 30000);

INSERT INTO skis (id, category, company, title, length, price)
VALUES (3, 'freeride', 'k2', 'marksmanTest3', 150, 30000);

INSERT INTO role (id, name ) VALUES (1, 'admin');
INSERT INTO role (id, name ) VALUES (2, 'user');

INSERT INTO users (email, full_name, password)
VALUES ('admin@mail', 'admin', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi');

INSERT INTO users (email, full_name, password)
VALUES ('user@mail', 'user', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi');

INSERT INTO user_basket_item (id, user_id, ski_id, amount)
VALUES (1, 1, 1, 1);

INSERT INTO user_basket_item (id, user_id, ski_id, amount)
VALUES (2, 1, 2, 2);

INSERT INTO user_basket_item (id, user_id, ski_id, amount)
VALUES (3, 2, 1, 1);

INSERT INTO user_basket_item (id, user_id, ski_id, amount)
VALUES (4, 2, 2, 2);

