CREATE TABLE lesson(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    unit_id UUID NOT NULL,
    FOREIGN KEY (unit_id) REFERENCES unit(id) ON DELETE CASCADE
);