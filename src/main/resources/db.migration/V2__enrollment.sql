CREATE TABLE enrollment (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    enrollment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deadline_for_completion TIMESTAMP NOT NULL,
    duration INT NOT NULL DEFAULT 365,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    course_id UUID,
    student_id UUID,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);