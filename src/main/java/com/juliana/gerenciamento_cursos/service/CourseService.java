package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.CourseRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.CourseResponse;
import com.juliana.gerenciamento_cursos.domain.client.Teacher;
import com.juliana.gerenciamento_cursos.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.repository.TeacherRepository;
import com.juliana.gerenciamento_cursos.repository.TeacherCourseRepository;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.NoUpdateRequiredException;
import com.juliana.gerenciamento_cursos.exceptions.TitleAlreadyInUseException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class CourseService {
    @Autowired
    CourseRepository repository;

    @Autowired
    TeacherCourseRepository teacherCourseRepository;

    @Autowired
    TeacherRepository teacherRepository;

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

    public List<Course> showCourses() {
        List<Course> courses = repository.findAll();

        if(courses.isEmpty()){
            throw new EmptyListException("Nenhum curso encontrado");
        }

        return  courses;
    }

    public Course findCourseByTitle(String title){
        return repository.findByTitle(title)
                .orElseThrow(()-> new InexistentOptionException("Nenhum curso encontrado"));
    }

    public void addTeacher(UUID teacherId, UUID courseId){
        teacherCourseRepository.addTeacherToCourse(teacherId, courseId);
    }

    public void removeTeacher(UUID teacherId, UUID courseId){
        teacherCourseRepository.removeTeacherFromCourse(teacherId, courseId);
    }

    public List<String> showTeachersOfCourse(UUID courseId) throws InexistentOptionException {
        List<UUID> idTeachers = teacherCourseRepository.findTeachersByCourseId(courseId);

        if(idTeachers.isEmpty()){
            throw new InexistentOptionException("Nada encontrado");
        }

        return idTeachers.stream()
                .map(id -> {
                    try {
                        return teacherRepository.findById(id).orElseThrow(() -> new InexistentOptionException("Nada encontrado"));
                    } catch (InexistentOptionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Teacher::getName)
                .toList();
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
}
