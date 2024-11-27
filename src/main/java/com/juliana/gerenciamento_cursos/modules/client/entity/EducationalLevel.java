package com.juliana.gerenciamento_cursos.modules.client.entity;

public enum EducationalLevel {
    ELEMENTARY("Elementary School"),
    HIGH_SCHOOL("High School"),
    UNDERGRADUATE("Undergraduate"),
    MASTERS("Master's Degree"),
    DOCTORATE("Doctorate");

    private final String degree;

    EducationalLevel(String degree){
        this.degree = degree;
    }

    public String getDegree(){
        return degree;
    }
}
