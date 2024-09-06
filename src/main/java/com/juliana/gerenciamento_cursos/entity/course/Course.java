package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.entity.teacher.Teacher;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Course {
    private UUID id;
    private String title;
    private String description;
    private Teacher teacher;
    private List<Enrollment> enrollments = new ArrayList<>();
    private List<ModuleSection> modules = new ArrayList<>();
    private EducationalPlatform educationalPlatform;

    public Course(String title, String description, String teacherEmail, EducationalPlatform educationalPlatform) throws InexistentOptionException{
        this.title = title;
        this.description = description;
        this.id = UUID.randomUUID();
        this.teacher = educationalPlatform.verifyExistenceOfTeacher(teacherEmail);
        this.educationalPlatform = educationalPlatform;
        educationalPlatform.getAllCourses().add(this);
        teacher.getCoursesTaught().add(this);
    }

    public ModuleSection verifyExistenceOfModule(String title) throws InexistentOptionException{
        for(ModuleSection module : modules){
            if(title.equalsIgnoreCase(module.getTitle())){
                return module;
            }
        }
        throw new InexistentOptionException("Modulo não encontrado");
    }

    public String showEnrollments(){
        StringBuilder listEnrollments = new StringBuilder();
        for(Enrollment enrollment: enrollments){
            listEnrollments.append("-").append(enrollment.getStudent().getName()).append(" Data inscrição: ").append(DateValidation.formatDate(enrollment.getEnrollmentDay())).append("\n");
        }
        return listEnrollments.toString();
    }

}
