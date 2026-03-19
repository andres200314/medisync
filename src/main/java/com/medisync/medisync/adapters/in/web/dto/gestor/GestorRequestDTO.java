package com.medisync.medisync.adapters.in.web.dto.gestor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GestorRequestDTO {
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String email;
    private String passwordHash;
    private BigDecimal latitud;
    private BigDecimal longitud;
}