package com.juliana.gerenciamento_cursos.modules.course.service;

import com.juliana.gerenciamento_cursos.exceptions.NoUpdateNeededException;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherDTO;
import com.juliana.gerenciamento_cursos.modules.client.entity.Teacher;
import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseRequestPayload;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseResponse;
import com.juliana.gerenciamento_cursos.modules.course.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.modules.client.repository.TeacherRepository;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.TitleAlreadyInUseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository repository;

    private final TeacherRepository teacherRepository;

    public CourseResponse createNewCourse(@Valid CourseRequestPayload courseRequestPayload){
        validateUniqueTitle(courseRequestPayload.title());

        var course = Course.builder()
                .title(courseRequestPayload.title())
                .description(courseRequestPayload.description())
                .teachers(new HashSet<>())
                .build();

        repository.save(course);
        return new CourseResponse(course.getId());
    }

    public void deleteCourse(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public void alterDescription(UUID id, String description) {
        Course course = validateId(id);

        if(course.getDescription().equals(description)){
            throw new NoUpdateNeededException("A nova descrição não pode ser igual à descrição atual");
        }

        course.setDescription(description);
        repository.save(course);
    }

    public List<CourseDTO> showAllCourses() {
        List<CourseDTO> courses = repository.findAll().stream()
                .map(this::convertToDTO)
                .toList();

        if(courses.isEmpty()){
            throw new EmptyListException("Nenhum curso encontrado");
        }

        return courses;
    }

    public CourseDTO findCourseByTitle(String title){
        Course course = repository.findByTitleContainsIgnoreCase(title)
                .orElseThrow(()-> new NoSuchElementException("Nenhum curso encontrado"));

        return convertToDTO(course);
    }

    public void addTeacher(UUID teacherId, UUID courseId){
        Course course = validateId(courseId);
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new NoSuchElementException("Professor não encontrado"));

        if(!course.getTeachers().add(teacher)){
            throw new NoUpdateNeededException(String.format("O usuário do '%s' já é professor do curso '%s'", teacherId, courseId));
        }

        course.getTeachers().add(teacher);
        repository.save(course);
    }

    public void removeTeacher(UUID teacherId, UUID courseId){
        Course course = validateId(courseId);
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new NoSuchElementException("Professor não encontrado"));

        if(!course.getTeachers().remove(teacher)){
            throw new NoSuchElementException(String.format("O usuário '%S' não leciona no curso '%s'", teacherId, courseId));
        }

        course.getTeachers().remove(teacher);
        repository.save(course);
    }

    public Set<TeacherDTO> showTeachersOfCourse(UUID courseId) throws EmptyListException {
        Course course = validateId(courseId);

        Set<TeacherDTO> teachers = course.getTeachers().stream()
                .map(t -> new TeacherDTO(t.getName(), t.getUsername(), t.getEmail(), t.getDateOfBirth(), t.getSkills()))
                .collect(Collectors.toSet());

        if(teachers.isEmpty()){
            throw new EmptyListException("Nada encontrado");
        }

        return teachers;
    }

    private void validateUniqueTitle(String title) {
        if (repository.existsByTitle(title)) {
            throw new TitleAlreadyInUseException("Esse curso já existe");
        }
    }

    private Course validateId(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Esse curso não existe"));
    }

    private CourseDTO convertToDTO(Course course){
        return new CourseDTO(course.getTitle(),
                course.getDescription(),
                course.getCreatedAt());
    }
}
