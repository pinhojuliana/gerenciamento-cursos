package com.juliana.gerenciamento_cursos.entity.user.teacher;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.exceptions.EmailAlreadyInUseException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.exceptions.UsernameAlreadyInUseException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class TeacherService {
    @Autowired
    TeacherRepository repository;

    public UserResponse createNewTeacher(UserRequestPayload userRequestPayload) throws UnderageException {
        validateUniqueUsername(userRequestPayload.username());
        validateUniqueEmail(userRequestPayload.email());

        Teacher newTeacher = new Teacher(userRequestPayload.name(),
                userRequestPayload.username(),
                userRequestPayload.email(),
                userRequestPayload.password(),
                userRequestPayload.dateOfBirth());

        repository.save(newTeacher);

        return new UserResponse(newTeacher.getId());
    }

    public void deleteTeacher(UUID id) {
        repository.deleteById(id);
    }

    public void addSkill(UUID id, String skill) throws InexistentOptionException {
        Teacher teacher = repository.findById(id).orElseThrow(() -> new InexistentOptionException("Esse usuário não existe"));
        teacher.getSkills().add(skill);
        repository.save(teacher);
    }

    public void removeSkill(UUID id, String skill) throws InexistentOptionException {
        Teacher teacher = repository.findById(id).orElseThrow(() -> new InexistentOptionException("Esse usuário não existe"));
        teacher.getSkills().remove(skill);
        repository.save(teacher);
    }

    public List<Teacher> findTeacher(String name){
        return repository.findByName(name);
    }

    public void findTeacherCourse(Course course, String nameTeacher){

    }

    public void showAllCoursesTaught(UUID id){
        
    }

    private void validateUniqueUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new UsernameAlreadyInUseException("Esse username está sendo utilizado por outro usuário");
        }
    }

    private void validateUniqueEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException("Esse e-mail está sendo utilizado por outro usuário");
        }
    }
}
