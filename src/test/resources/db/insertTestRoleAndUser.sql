INSERT INTO role (id, name)
VALUES (1, 'admin'),
       (2, 'user');

INSERT INTO users (email, full_name, password)
VALUES ('admin@mail', 'admin', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi'),
       ('user@mail', 'user', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi');