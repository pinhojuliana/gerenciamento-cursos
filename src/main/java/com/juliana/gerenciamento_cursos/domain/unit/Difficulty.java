package com.juliana.gerenciamento_cursos.domain.unit;

import lombok.Getter;

@Getter
public enum Difficulty {
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED");

    private final String name;

    Difficulty(String name){
        this.name = name;
    }

}
