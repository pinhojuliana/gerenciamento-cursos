CREATE TABLE class_section(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    module_id UUID,
    FOREIGN KEY (module_id) REFERENCES modules(id) ON DELETE CASCADE
);