create table users (
    id INTEGER NOT NULL,
    description TEXT,
    name TEXT,
    age INTEGER,
    CONSTRAINT users_id_pk PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS users_seq INCREMENT BY 50;