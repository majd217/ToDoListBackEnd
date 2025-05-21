CREATE TABLE IF NOT EXISTS meal (
    id serial PRIMARY KEY,
    mealdate date NOT NULL,
    label TEXT NOT NULL
);
