package com.juliana.gerenciamento_cursos.entity.module_section;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@NoArgsConstructor
public class ModuleSectionService {
    @Autowired
    ModuleSectionRepository moduleRepository;

    public ModuleSectionResponse createModule(ModuleSectionRequestPayload moduleRequestPayload, Course course){
        ModuleSection newModule = new ModuleSection(moduleRequestPayload.title(), moduleRequestPayload.description(), moduleRequestPayload.difficulty(), course);
        moduleRepository.save(newModule);

        return new ModuleSectionResponse(newModule.getId());
    }

    public void deleteModule(UUID id){
        moduleRepository.deleteById(id);
    }

    public void showModules(Course course){

    }

    public void findModules(Course course, String title){

    }

    public void findModules(Course course, Difficulty difficulty){

    }

    public void verifyExistenceOfModule(ModuleSection module) throws InexistentOptionException {

    }
}
