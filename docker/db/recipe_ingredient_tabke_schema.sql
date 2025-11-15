\i recipe_table_schema.sql
\i ingredient_table_schema.sql


CREATE TABLE IF NOT EXISTS recipe_ingredient (
    id SERIAL PRIMARY KEY,
    recipe_id INT NOT NULL REFERENCES recipe(id),
    ingredient_id INT NOT NULL REFERENCES ingredient(id)
);
