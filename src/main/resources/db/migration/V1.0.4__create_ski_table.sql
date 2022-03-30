CREATE TABLE skis
(
    id         SERIAL PRIMARY KEY,
    categories VARCHAR(100)        NOT NULL,
    company    VARCHAR(100)        NOT NULL,
    title      VARCHAR(100) UNIQUE NOT NULL,
    length     INT                 NOT NULL,
    price      DECIMAL             NOT NULL
);