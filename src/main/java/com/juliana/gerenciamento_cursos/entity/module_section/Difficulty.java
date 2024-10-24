package com.juliana.gerenciamento_cursos.entity.module_section;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;

//verificar como funcionam enums com spring
public enum Difficulty {
    BEGINNER("Beginner"),
    INTERMEDIARY("Intermediate"),
    ADVANCED("Advanced");

    private final String label;

    Difficulty(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
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
