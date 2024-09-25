package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;

public enum Dificultity {
    BEGINNER("Beginner"),
    INTERMEDIARY("Intermediate"),
    ADVANCED("Advanced");

    private final String label;

    Dificultity(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Dificultity validDificultityValue(String value) throws InexistentOptionException{
        for(Dificultity dificultity : Dificultity.values()) {
            if (dificultity.getLabel().equalsIgnoreCase(value)) {
                return dificultity;
            }
        }
        throw new InexistentOptionException("Valor não encontrado. As opções válidas são: Beginner, Intermediate e Advanced");
    }
}
