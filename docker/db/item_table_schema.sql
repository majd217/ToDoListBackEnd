\i store_table_schema.sql

CREATE TABLE IF NOT EXISTS item (
    id serial PRIMARY KEY ,
    label text NOT NULL,
    storeid integer REFERENCES store(id),
    checked boolean
);
