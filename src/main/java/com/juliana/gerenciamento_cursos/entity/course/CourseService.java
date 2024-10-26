package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.entity.user.teacher.Teacher;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
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
        repository.deleteById(id);
    }

    public void alterDescription(UUID id, String description) throws InexistentOptionException {
        Course course = repository.findById(id).orElseThrow(() -> new InexistentOptionException("Esse curso não existe"));
        course.setDescription(description);
        repository.save(course);
    }

    public List<Course> showCourses() {
        return repository.findAll();
    }

    private void validateUniqueTitle(String title) {
        if (repository.existsByTitle(title)) {
            throw new TitleAlreadyInUseException("Esse curso já existe");
        }
    }

}
