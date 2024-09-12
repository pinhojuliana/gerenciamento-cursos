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
    private List<Enrollment> enrollments;
    private List<ModuleSection> modules;

    public Course(String title, String description, Teacher teacher){
        this.title = title;
        this.description = description;
        this.id = UUID.randomUUID();
        this.teacher = teacher;
        enrollments = new ArrayList<>();
        modules = new ArrayList<>();
    }

    public ModuleSection verifyExistenceOfModule(String title) throws InexistentOptionException{
        for(ModuleSection module : modules){
            if(title.equalsIgnoreCase(module.getTitle())){
                return module;
            }
        }
        throw new InexistentOptionException("Modulo não encontrado");
    }

    //se depois eu precisar desse metodo para algo ajusto pra retornar a lista em si
    public String showEnrollments(){
        StringBuilder listEnrollments = new StringBuilder();
        for(Enrollment enrollment: enrollments){
            listEnrollments.append("\n{").append(enrollment.getStudent().getName()).append(" - ").append(DateValidation.formatDateTime(enrollment.getEnrollmentDateTime())).append("Válido: ").append(enrollment.isActive()).append("}");
        }
        return listEnrollments.toString();
    }

    @Override
    public String toString(){
        return String.format("Title: %s\nDescription: %s\nTeacher: %s", title, description, teacher.getEmail());
    }

}
