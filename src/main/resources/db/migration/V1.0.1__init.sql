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