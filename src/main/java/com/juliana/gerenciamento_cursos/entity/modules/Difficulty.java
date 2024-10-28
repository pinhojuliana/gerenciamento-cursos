package com.juliana.gerenciamento_cursos.entity.modules;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.Getter;

@Getter
public enum Difficulty {
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED");

    private final String label;

    Difficulty(String label){
        this.label = label;
    }

    public static Difficulty validDifficultyValue(String value) throws InexistentOptionException{
        for(Difficulty difficulty : Difficulty.values()) {
            if (difficulty.getLabel().equalsIgnoreCase(value)) {
                return difficulty;
            }
        }
        throw new InexistentOptionException("Valor não encontrado. As opções válidas são: Beginner, Intermediate e Advanced");
    }
}
