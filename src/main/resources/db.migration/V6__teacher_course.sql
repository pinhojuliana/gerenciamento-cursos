CREATE TABLE teacher_course(
teacher_id UUID,
course_id UUID,
FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);