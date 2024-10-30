package com.juliana.gerenciamento_cursos.entity.teacher;

import com.juliana.gerenciamento_cursos.entity.client.ClientRequestPayload;
import com.juliana.gerenciamento_cursos.entity.client.ClientResponse;
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

    public ClientResponse createNewTeacher(ClientRequestPayload userRequestPayload) throws UnderageException {
        validateUniqueUsername(userRequestPayload.username());
        validateUniqueEmail(userRequestPayload.email());

        Teacher newTeacher = new Teacher(userRequestPayload.name(),
                userRequestPayload.username(),
                userRequestPayload.email(),
                userRequestPayload.password(),
                userRequestPayload.dateOfBirth());

        repository.save(newTeacher);

        return new ClientResponse(newTeacher.getId());
    }

    public void deleteTeacher(UUID id) {
        validateId(id);
        repository.deleteById(id);
    }

    public void addSkill(UUID id, String skill) throws InexistentOptionException {
        Teacher teacher = validateId(id);
        teacher.getSkills().add(skill);
        repository.save(teacher);
    }

    public void removeSkill(UUID id, String skill) throws InexistentOptionException {
        Teacher teacher = validateId(id);
        teacher.getSkills().remove(skill);
        repository.save(teacher);
    }

    public void updateTeacherUsername(UUID id, String username) throws InexistentOptionException {
        Teacher teacher = validateId(id);
        checkForNoUpdate(teacher.getUsername(), username);
        validateUniqueUsername(username);
        teacher.setUsername(username);
        repository.save(teacher);
    }

    public void updateTeacherEmail(UUID id, String email) throws InexistentOptionException {
        Teacher teacher = validateId(id);
        checkForNoUpdate(teacher.getEmail(), email);
        validateUniqueEmail(email);
        teacher.setEmail(email);
        repository.save(teacher);
    }

    public void updateTeacherPassword(UUID id, String oldPassword, String newPassword) throws RuntimeException {
        Teacher teacher = validateId(id);
        checkForNoUpdate(teacher.getPassword(), newPassword);
        if(!teacher.getPassword().equals(oldPassword)){
            throw new InvalidPasswordException("A senha atual está incorreta");
        }
        teacher.setPassword(newPassword);
        repository.save(teacher);
    }

    public List<TeacherDTO> findTeacher(String name){
        List<TeacherDTO> teachers = repository.findByName(name).stream()
                .map(this::convertToDTO)
                .toList();
        if(teachers.isEmpty()){
            throw new EmptyListException("Nenhum professor com esse nome foi encontrado");
        }
        return teachers;
    }

    public List<TeacherDTO> getAllTeachers() throws EmptyListException {
        List<TeacherDTO> teachers = repository.findAll().stream().map(this::convertToDTO)
                .toList();

        if (teachers.isEmpty()) {
            throw new EmptyListException("Não há estudantes cadastrados");
        }

        return teachers;
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

    private Teacher validateId(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
    }

    private <T> void checkForNoUpdate(T oldValue, T newValue) {
        if (oldValue.equals(newValue)) {
            throw new NoUpdateRequiredException("Os campos 'novo' e 'atual' não devem ser iguais");
        }
    }

    private TeacherDTO convertToDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getName(),
                teacher.getUsername(),
                teacher.getEmail(),
                teacher.getDateOfBirth(),
                teacher.getSkills()
        );
    }
}
