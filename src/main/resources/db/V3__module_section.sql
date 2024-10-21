CREATE TABLE module_section(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOL NULL,
    dificultity dificultity NOT NULL ,
    course_id,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);