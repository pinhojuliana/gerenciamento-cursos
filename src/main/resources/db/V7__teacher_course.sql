CREATE TABLE teacher_course(
teacher_id,
course_id,
PRIMARY KEY (course_id, teacher id),
FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);