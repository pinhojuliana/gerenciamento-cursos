package com.juliana.gerenciamento_cursos.entity.student;

public enum EducationalLevel {
    FUNDAMENTAL("Fundamental"),
    AVERAGE("Average"),
    HIGHER("Higher"),
    MASTERS_DEGREE("Master's Degree"),
    DOCTORATE("Doctorate");

    private final String degree;

    EducationalLevel(String degree){
        this.degree = degree;
    }

    public String getDegree(){
        return degree;
    }
}
