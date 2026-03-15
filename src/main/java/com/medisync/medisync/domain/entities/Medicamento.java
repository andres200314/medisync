package com.medisync.medisync.domain.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Entity
@Table(name = "medicamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicamento {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "requiere_formula", nullable = false)
    private Boolean requiereFormula;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
