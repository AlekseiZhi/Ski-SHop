CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    email     VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100)        NOT NULL,
    password  VARCHAR(100)        NOT NULL
);

CREATE TABLE role
(
    id   SERIAL PRIMARY KEY,
    name varchar(100) not null
);

CREATE TABLE user_role
(
    user_id INT NOT NULL REFERENCES users (id),
    role_id INT NOT NULL REFERENCES role (id)
);

INSERT INTO users (id, email, full_name, password)
VALUES (1, 'admin@mail', 'admin', '$2a$10$thwp32FYaBkCI9.zaJW.ReR90HTPywIveWoApdjA/.2NTmuMjaGoi');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);