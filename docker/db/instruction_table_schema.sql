\i recipe_table_schema.sql

CREATE TABLE IF NOT EXISTS instruction (
    id SERIAL PRIMARY KEY,
    description TEXT,
    recipe_id INT NOT NULL REFERENCES recipe(id),
    step_number integer NOT NULL
);
