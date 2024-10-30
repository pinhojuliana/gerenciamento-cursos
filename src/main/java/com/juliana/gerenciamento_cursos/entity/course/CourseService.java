package com.juliana.gerenciamento_cursos.entity.course;

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
