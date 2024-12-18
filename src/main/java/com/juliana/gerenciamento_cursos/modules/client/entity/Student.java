package com.juliana.gerenciamento_cursos.modules.client.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student extends Client {
    @NotBlank(message = "O campo 'description' não pode estar vazio")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "educational_level")
    private EducationalLevel educationalLevel;
}
