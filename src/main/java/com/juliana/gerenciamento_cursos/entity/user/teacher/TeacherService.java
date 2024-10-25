package com.juliana.gerenciamento_cursos.entity.user.teacher;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.entity.user.student.EducationalLevel;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    public UserResponse createNewTeacher(UserRequestPayload userRequestPayload) throws UnderageException {
        Teacher newTeacher = new Teacher(userRequestPayload.name(),
                userRequestPayload.username(),
                userRequestPayload.email(),
                userRequestPayload.password(),
                userRequestPayload.dateOfBirth());

        teacherRepository.save(newTeacher);

        return new UserResponse(newTeacher.getId());
    }

    public void deleteTeacher(UUID id) {
        teacherRepository.deleteById(id);
    }

    public void addSkill(String skill){

    }

    public void removeSkill(String skill) {

    }

    public void verifyExistenceOfTeacher(String email) throws InexistentOptionException {

    }

    public void verifyExistenceOfTeacher(UUID id) throws InexistentOptionException {

    }

    public void findTeacher(String username){

    }

    public void findTeacherCourse(Course course, String nameTeacher){

    }

    public void showAllCoursesTaught(Teacher teacher){

    }
}
