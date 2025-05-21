do $$
begin
IF (EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_NAME = 'store'))
THEN
   return;
END IF;

CREATE TABLE IF NOT EXISTS store (
    id serial PRIMARY KEY,
    name TEXT NOT NULL,
    type INT NOT NULL
);

INSERT INTO store (name,type) VALUES
    ('DM', 1),
    ('Nahkauf', 0),
    ('Arabic Shop', 0),
    ('Sparparadies', 2);

end
$$
