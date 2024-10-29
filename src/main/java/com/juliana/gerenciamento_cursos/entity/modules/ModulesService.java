package com.juliana.gerenciamento_cursos.application.entity.modules;

import com.juliana.gerenciamento_cursos.application.entity.course.Course;
import com.juliana.gerenciamento_cursos.application.exceptions.InexistentOptionException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class ModulesService {
    @Autowired
    ModulesRepository repository;

    public ModulesResponse createNewModule(ModulesRequestPayload moduleRequestPayload, Course course){
        Modules newModule = new Modules(moduleRequestPayload.title(), moduleRequestPayload.description(), moduleRequestPayload.difficulty(), course);
        repository.save(newModule);

        return new ModulesResponse(newModule.getId());
    }

    public void deleteModule(UUID id){
        repository.deleteById(id);
    }

    public List<Modules> showModules(UUID courseId){
        return repository.findByCourseId(courseId);
    }

    public List<Modules> findModuleCourse(UUID courseId, String title) throws InexistentOptionException {
        return repository.findByCourseId(courseId).stream()
                .filter(m -> m.getCourse().getTitle().equalsIgnoreCase(title))
                .toList();
    }

    public List<Modules> findModule(String title) throws InexistentOptionException {
        List<Modules> modules = repository.findByTitle(title);
        if(modules.isEmpty()){
            throw new InexistentOptionException("Modulo não encontrado");
        }
        return modules;
    }

}
