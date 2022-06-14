INSERT INTO ski (id, category, company, title, length, price)
VALUES (1, 'freeride', 'k2', 'marksmanTest1', 180, 35000),
       (2, 'freeride', 'k2', 'marksmanTest2', 150, 30000),
       (3, 'freeride', 'k2', 'marksmanTest3', 150, 30000);

INSERT INTO role (id, name)
VALUES (1, 'admin'),
       (2, 'user');

INSERT INTO users (email, full_name, password)
VALUES ('admin@mail', 'admin', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi'),
       ('user@mail', 'user', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi');