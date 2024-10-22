CREATE TABLE class_section(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    module_id UUID,
    FOREIGN KEY (module_id) REFERENCES module_section(id) ON DELETE CASCADE
);