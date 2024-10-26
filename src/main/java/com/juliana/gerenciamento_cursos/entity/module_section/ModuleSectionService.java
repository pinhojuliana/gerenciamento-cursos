package com.juliana.gerenciamento_cursos.entity.module_section;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class ModuleSectionService {
    @Autowired
    ModuleSectionRepository repository;

    public ModuleSectionResponse createNewModule(ModuleSectionRequestPayload moduleRequestPayload, Course course){
        ModuleSection newModule = new ModuleSection(moduleRequestPayload.title(), moduleRequestPayload.description(), moduleRequestPayload.difficulty(), course);
        repository.save(newModule);

        return new ModuleSectionResponse(newModule.getId());
    }

    public void deleteModule(UUID id){
        repository.deleteById(id);
    }

    public List<ModuleSection> showModules(UUID courseId){
        return repository.findByCourseId(courseId);
    }

    public List<ModuleSection> findModuleCourse(UUID courseId, String title) throws InexistentOptionException {
        return repository.findByCourseId(courseId).stream()
                .filter(m -> m.getCourse().getTitle().equalsIgnoreCase(title))
                .toList();
    }

    public List<ModuleSection> findModule(String title) throws InexistentOptionException {
        List<ModuleSection> modules = repository.findByTitle(title);
        if(modules.isEmpty()){
            throw new InexistentOptionException("Modulo não encontrado");
        }
        return modules;
    }

}
