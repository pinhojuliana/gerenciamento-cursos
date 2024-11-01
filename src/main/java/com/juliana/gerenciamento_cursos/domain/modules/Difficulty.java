package com.juliana.gerenciamento_cursos.domain.modules;

public enum Difficulty {
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED");

    private final String name;

    Difficulty(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
