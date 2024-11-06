package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.domain.modules.Modules;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.ModulesRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.ModulesResponse;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.ModulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModulesService {
    private final ModulesRepository repository;

    public ModulesResponse createNewModule(ModulesRequestPayload moduleRequestPayload, Course course){
        Modules newModule = new Modules(moduleRequestPayload.title(), moduleRequestPayload.description(), moduleRequestPayload.difficulty(), course);
        repository.save(newModule);

        return new ModulesResponse(newModule.getId());
    }

    public void deleteModule(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public List<Modules> findModulesByCourse(UUID courseId){
       return repository.findByCourseId(courseId)
               .orElseThrow(() -> new InexistentOptionException("Nenhum modulo encontrado"));
    }

    public List<Modules> findModuleCourse(UUID courseId, String title) throws InexistentOptionException {
        List<Modules> modules = findModulesByCourse(courseId);
        return modules.stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .toList();
    }

    private void validateId(UUID id){
        if (!repository.existsById(id)) {
            throw new InexistentOptionException("Modulo não encontrado");
        }
    }

}
