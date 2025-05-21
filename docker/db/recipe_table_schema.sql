CREATE TABLE IF NOT EXISTS recipe (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    ingredients TEXT[] NOT NULL,
    instructions TEXT[] NOT NULL
);
