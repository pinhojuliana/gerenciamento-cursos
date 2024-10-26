package com.juliana.gerenciamento_cursos.entity.user.teacher;

import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.exceptions.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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

    public void updateTeacherUsername(UUID id, String username) throws InexistentOptionException {
        validateUniqueUsername(username);
        Teacher teacher = repository.findById(id).orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
        teacher.setUsername(username);
        repository.save(teacher);
    }

    public void updateTeacherEmail(UUID id, String email) throws InexistentOptionException {
        validateUniqueEmail(email);
        Teacher teacher = repository.findById(id).orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
        teacher.setEmail(email);
        repository.save(teacher);
    }

    public void updateTeacherPassword(UUID id, String oldPassword, String newPassword) throws InexistentOptionException {
        Teacher teacher = repository.findById(id).orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
        if(!teacher.getPassword().equals(oldPassword)){
            throw new InvalidPasswordException("A senha atual está incorreta");
        }
        teacher.setPassword(newPassword);
        repository.save(teacher);
    }

    public List<Teacher> findTeacher(String name){
        return repository.findByName(name).stream()
                .map(t -> new Teacher(t.getName(), t.getUsername(), t.getEmail(), t.getDateOfBirth(), t.getSkills()))
                .toList();
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
