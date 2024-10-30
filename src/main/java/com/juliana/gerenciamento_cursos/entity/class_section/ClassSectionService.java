package com.juliana.gerenciamento_cursos.entity.class_section;

import com.juliana.gerenciamento_cursos.entity.modules.Modules;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClassSectionService {
    @Autowired
    ClassSectionRepository repository;

    public ClassSectionResponse createNewClass(ClassSectionRequestPayload classRequestPayload, Modules modules){
        ClassSection newClass = new ClassSection(classRequestPayload.title(), classRequestPayload.description(), modules);
        repository.save(newClass);

        return new ClassSectionResponse(newClass.getId());
    }

    public void deleteClass(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public List<ClassSection> showClassesOfModule(UUID moduleId) throws InexistentOptionException {
        List<ClassSection> classes = repository.findByModules_Id(moduleId);
        if (classes.isEmpty()){
            throw new InexistentOptionException("Nenhuma aula encontrada");
        }
        return classes;
    }

    public List<ClassSection> findClassesByTitle(UUID moduleSectionId ,String title) throws InexistentOptionException {
        List<ClassSection> classes = repository.findByTitle(title).stream()
                .filter(c -> c.getModules().getId().equals(moduleSectionId))
                .toList();
        if (classes.isEmpty()){
            throw new InexistentOptionException("Nenhuma aula encontrada");
        }
        return classes;
    }

    private ClassSection validateId(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new InexistentOptionException("Modulo não encontrado"));
    }

}
