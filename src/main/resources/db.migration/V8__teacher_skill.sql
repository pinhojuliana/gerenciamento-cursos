CREATE TABLE teacher_skill(
    teacher_id UUID,
    skill VARCHAR(50) NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE
);