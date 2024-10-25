package com.juliana.gerenciamento_cursos.entity.class_section;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.module_section.ModuleSection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class ClassSectionService {
    @Autowired
    ClassSectionRepository classRepository;

    public ClassSectionResponse createClass(ClassSectionRequestPayload classRequestPayload, ModuleSection module){
        ClassSection newClass = new ClassSection(classRequestPayload.title(), classRequestPayload.description(), module);
        classRepository.save(newClass);

        return new ClassSectionResponse(newClass.getId());
    }

    public void deleteClass(UUID id){
        classRepository.deleteById(id);
    }

    public void showClasses(ModuleSection moduleSection){

    }

    public void findClasses(Course course, ModuleSection moduleSection ,String title){
        //retorna um item
    }

    public void findClasses(Course course, String title){
        //pode ter uma lista
    }

    public void verifyExistenceOfClass(ClassSection classSection){
        //boolean ou obj?
    }
}
