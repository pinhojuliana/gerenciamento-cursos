package com.juliana.gerenciamento_cursos.modules.client.service;

import com.juliana.gerenciamento_cursos.modules.course.dto.CourseDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherRequestPayload;
import com.juliana.gerenciamento_cursos.modules.client.dto.ClientResponse;
import com.juliana.gerenciamento_cursos.modules.client.entity.Teacher;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherDTO;
import com.juliana.gerenciamento_cursos.exceptions.*;
import com.juliana.gerenciamento_cursos.modules.client.repository.TeacherRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.juliana.gerenciamento_cursos.util.AgeValidation.validateAge;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository repository;

    private final PasswordEncoder passwordEncoder;

    public ClientResponse createNewTeacher(@Valid TeacherRequestPayload requestPayload) throws InvalidAgeException {
        validateUniqueUsername(requestPayload.username());
        validateUniqueEmail(requestPayload.email());
        validateAge(requestPayload.dateOfBirth());

        var teacher = Teacher.builder()
                .name(requestPayload.name())
                .username(requestPayload.username())
                .email(requestPayload.email())
                .password(passwordEncoder.encode(requestPayload.password()))
                .dateOfBirth(requestPayload.dateOfBirth())
                .build();

        repository.save(teacher);

        return new ClientResponse(teacher.getId());
    }

    public void deleteTeacher(UUID id) {
        validateId(id);
        repository.deleteById(id);
    }

    public void addSkill(UUID id, String skill) throws SkillAlreadyExistsException, IllegalArgumentException{
        Teacher teacher = validateId(id);
        String skillParsed = skill.trim();

        if(teacher.getSkills().contains(skillParsed)){
            throw new SkillAlreadyExistsException(String.format("A skill '%s' já existe neste perfil", skill));
        }

        teacher.getSkills().add(skillParsed);
        repository.save(teacher);
    }

    public void removeSkill(UUID id, String skill) throws NoSuchElementException {
        Teacher teacher = validateId(id);
        String skillParsed = skill.trim();

        if(!teacher.getSkills().contains(skillParsed)){
            throw new NoSuchElementException(String.format("A skill '%s' não foi encontrada", skill));
        }

        teacher.getSkills().remove(skillParsed);
        repository.save(teacher);
    }

    public void updateTeacherUsername(UUID id, String username) throws NoSuchElementException {
        Teacher teacher = validateId(id);
        checkForNoUpdate(teacher.getUsername(), username);
        validateUniqueUsername(username);
        teacher.setUsername(username);
        repository.save(teacher);
    }

    public void updateTeacherEmail(UUID id, String email) throws NoSuchElementException {
        Teacher teacher = validateId(id);
        checkForNoUpdate(teacher.getEmail(), email);
        validateUniqueEmail(email);
        teacher.setEmail(email);
        repository.save(teacher);
    }

    public void updateTeacherPassword(UUID id, String oldPassword, String newPassword) throws InvalidPasswordException {
        Teacher teacher = validateId(id);
        if(!teacher.getPassword().equals(oldPassword)){
            throw new InvalidPasswordException("A senha atual está incorreta");
        }
        checkForNoUpdate(teacher.getPassword(), newPassword);
        teacher.setPassword(newPassword);
        repository.save(teacher);
    }

    public List<TeacherDTO> findTeacherByName(String name){
        List<TeacherDTO> teachers = repository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .toList();

        if(teachers.isEmpty()){
            throw new NoSuchElementException("Nenhum professor com esse nome foi encontrado");
        }

        return teachers;
    }

    public List<TeacherDTO> getAllTeachers() throws NoSuchElementException {
        List<TeacherDTO> teachers = repository.findAll().stream().map(this::convertToDTO)
                .toList();

        if (teachers.isEmpty()) {
            throw new NoSuchElementException("Não há professores cadastrados");
        }

        return teachers;
    }

    public Set<CourseDTO> showAllCoursesTaught(UUID teacherId) throws NoSuchElementException {
        Teacher teacher = validateId(teacherId);
        Set<CourseDTO> coursesTaught = teacher.getCoursesTaught().stream()
                .map(c -> new CourseDTO(c.getTitle(),
                        c.getDescription(),
                        c.getCreatedAt()))
                .collect(Collectors.toSet());

        if (coursesTaught.isEmpty()){
            throw new NoSuchElementException("Não há cursos ministrados.");
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
                .orElseThrow(()-> new NoSuchElementException("Esse usuário não existe"));
    }

    private <T> void checkForNoUpdate(T oldValue, T newValue) {
        if (oldValue.equals(newValue)) {
            throw new NoUpdateNeededException("Os campos 'novo' e 'atual' não devem ser iguais");
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
