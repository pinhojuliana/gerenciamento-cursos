package com.juliana.gerenciamento_cursos.entity.class_section;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.module_section.ModuleSection;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;

public class ClassSectionService {
    public void createClass(){

    }

    public void deleteClass(){

    }

    public void showClasses(ModuleSection moduleSection){

    }

    public void findClasses(Course course, ModuleSection moduleSection ,String title){
        //retorna um item
    }

    public void findClasses(Course course, String title){
        //pode ter uma lista
    }

    public void verifyExistenceOfClass(ClassSection classSection) throws InexistentOptionException {

    }
}
