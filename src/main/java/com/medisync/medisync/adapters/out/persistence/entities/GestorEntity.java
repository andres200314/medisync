package com.medisync.medisync.adapters.out.persistence.entities;

import com.medisync.medisync.domain.enums.EstadoGestor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "gestores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GestorEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String nit;

    private String direccion;

    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoGestor estado;

    private BigDecimal latitud;

    private BigDecimal longitud;
}
