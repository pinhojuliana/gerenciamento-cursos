package com.juliana.gerenciamento_cursos.modules.client.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Manager extends Client{
    @NotNull
    @NotBlank(message = "O campo 'designation' não pode estar vazio")
    private String designation;
}
