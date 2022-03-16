INSERT INTO users (id, email, full_name, password)
VALUES (1, 'admin@mail', 'admin', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);