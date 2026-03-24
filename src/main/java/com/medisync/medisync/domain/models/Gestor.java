package com.medisync.medisync.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gestor {

    private UUID id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String email;
    private String passwordHash;
    private BigDecimal latitud;
    private BigDecimal longitud;
}