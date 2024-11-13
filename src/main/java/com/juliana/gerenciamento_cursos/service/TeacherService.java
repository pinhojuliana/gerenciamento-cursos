package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.CourseDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.TeacherRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.ClientResponse;
import com.juliana.gerenciamento_cursos.domain.client.Teacher;
import com.juliana.gerenciamento_cursos.DTOs.TeacherDTO;
import com.juliana.gerenciamento_cursos.exceptions.*;
import com.juliana.gerenciamento_cursos.repository.TeacherRepository;
import com.juliana.gerenciamento_cursos.util.AgeValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository repository;

    public ClientResponse createNewTeacher(@Valid TeacherRequestPayload requestPayload) throws UnderageException {
        validateUniqueUsername(requestPayload.username());
        validateUniqueEmail(requestPayload.email());

        Teacher newTeacher = new Teacher(requestPayload.name(),
                requestPayload.username(),
                requestPayload.email(),
                requestPayload.password(),
                AgeValidation.validateAge(requestPayload.dateOfBirth())
        );

        repository.save(newTeacher);

        return new ClientResponse(newTeacher.getId());
    }

    public void deleteTeacher(UUID id) {
        validateId(id);
        repository.deleteById(id);
    }

    public void addSkill(UUID id, @NotBlank String skill) throws SkillAlreadyExistsException, IllegalArgumentException{
        Teacher teacher = validateId(id);
        String skillParsed = skill.trim();

        if(teacher.getSkills().contains(skillParsed)){
            throw new SkillAlreadyExistsException(String.format("A skill '%s' já existe neste perfil", skill));
        }

        teacher.getSkills().add(skillParsed);
        repository.save(teacher);
    }

    public void removeSkill(UUID id, @NotBlank String skill) throws InexistentOptionException {
        Teacher teacher = validateId(id);
        String skillParsed = skill.trim();

        if(!teacher.getSkills().contains(skillParsed)){
            throw new InexistentOptionException(String.format("A skill '%s' não foi encontrada", skill));
        }

        teacher.getSkills().remove(skillParsed);
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

    public void updateTeacherPassword(UUID id, @NotBlank String oldPassword, String newPassword) throws InvalidPasswordException {
        Teacher teacher = validateId(id);
        checkForNoUpdate(teacher.getPassword(), newPassword);
        if(!teacher.getPassword().equals(oldPassword)){
            throw new InvalidPasswordException("A senha atual está incorreta");
        }
        teacher.setPassword(newPassword);
        repository.save(teacher);
    }

    public List<TeacherDTO> findTeacherByName(String name){
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
            throw new EmptyListException("Não há professores cadastrados");
        }

        return teachers;
    }

    public Set<CourseDTO> showAllCoursesTaught(UUID teacherId) throws EmptyListException {
        Teacher teacher = validateId(teacherId);
        Set<CourseDTO> coursesTaught = teacher.getCoursesTaught().stream()
                .map(c -> new CourseDTO(c.getTitle(),
                        c.getDescription(),
                        c.getCreatedAt()))
                .collect(Collectors.toSet());

        if (coursesTaught.isEmpty()){
            throw new EmptyListException("Não há cursos ministrados.");
        }

        return coursesTaught;
    }

    private void validateUniqueUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new UsernameAlreadyInUseException("Esse username já está sendo utilizado por outro usuário");
        }
    }

    private void validateUniqueEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException("Esse e-mail já está sendo utilizado por outro usuário");
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
