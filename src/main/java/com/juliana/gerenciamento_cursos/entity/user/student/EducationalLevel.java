package com.juliana.gerenciamento_cursos.entity.user.student;

public enum EducationalLevel {
    FUNDAMENTAL("Fundamental"),
    AVERAGE("Average"),
    HIGHER("Higher"),
    MASTERS_DEGREE("Master's Degree"),
    DOCTORATE("Doctorate");

    private final String name;

    EducationalLevel(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
