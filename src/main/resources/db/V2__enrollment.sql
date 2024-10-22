CREATE TABLE enrollment (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    enrollmentDateTime TIMESTAMP NOT NULL,
    deadlineForCompletion TIMESTAMP NOT NULL,
    duration INT NOT NULL,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    course_id UUID,
    student_id UUID,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);