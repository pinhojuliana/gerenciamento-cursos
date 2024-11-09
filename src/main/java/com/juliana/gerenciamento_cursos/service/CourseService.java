package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.CourseDTO;
import com.juliana.gerenciamento_cursos.DTOs.TeacherDTO;
import com.juliana.gerenciamento_cursos.domain.client.Teacher;
import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.CourseRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.CourseResponse;
import com.juliana.gerenciamento_cursos.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.repository.TeacherRepository;
import com.juliana.gerenciamento_cursos.repository.TeacherCourseRepository;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.NoUpdateRequiredException;
import com.juliana.gerenciamento_cursos.exceptions.TitleAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository repository;

    private final TeacherCourseRepository teacherCourseRepository;

    private final TeacherRepository teacherRepository;

    public CourseResponse createNewCourse(CourseRequestPayload courseRequestPayload){
        validateUniqueTitle(courseRequestPayload.title());

        Course newCourse = new Course(courseRequestPayload.title(), courseRequestPayload.description());
        repository.save(newCourse);

        return new CourseResponse(newCourse.getId());
    }

    public void deleteCourse(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public void alterDescription(UUID id, String description) {
        Course course = validateId(id);

        if(course.getDescription().equals(description)){
            throw new NoUpdateRequiredException("A nova descrição não pode ser igual à descrição atual");
        }

        course.setDescription(description);
        repository.save(course);
    }

    public List<CourseDTO> showAllCourses() {
        List<CourseDTO> courses = repository.findAll().stream()
                .map(this::convertToDTO)
                .toList();;

        if(courses.isEmpty()){
            throw new EmptyListException("Nenhum curso encontrado");
        }

        return courses;
    }

    public CourseDTO findCourseByTitle(String title){
        Course course = repository.findByTitle(title)
                .orElseThrow(()-> new InexistentOptionException("Nenhum curso encontrado"));

        return convertToDTO(course);
    }

    public void addTeacher(UUID teacherId, UUID courseId){
        validateExistence(teacherId, courseId);
        teacherCourseRepository.addTeacherToCourse(teacherId, courseId);
    }

    public void removeTeacher(UUID teacherId, UUID courseId){
        validateExistence(teacherId, courseId);
        teacherCourseRepository.removeTeacherFromCourse(teacherId, courseId);
    }

    public List<TeacherDTO> showTeachersOfCourse(UUID courseId) throws EmptyListException {
        validateId(courseId);

        List<TeacherDTO> teachers = teacherCourseRepository.findTeachersByCourseId(courseId).stream()
                .map(t -> new TeacherDTO(t.getName(), t.getUsername(), t.getEmail(), t.getDateOfBirth(), t.getSkills()))
                .toList();

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
                .orElseThrow(() -> new InexistentOptionException("Esse curso não existe"));
    }

    private void validateExistence(UUID teacherId, UUID courseId){
        if(!repository.existsById(courseId) || !teacherRepository.existsById(teacherId)){
            throw new InexistentOptionException("Id não encontrado");
        }
    }

    private CourseDTO convertToDTO(Course course){
        return new CourseDTO(course.getTitle(),
                course.getDescription(),
                course.getCreatedAt());
    }
}
