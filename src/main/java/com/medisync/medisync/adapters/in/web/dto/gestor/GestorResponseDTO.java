package com.medisync.medisync.adapters.in.web.dto.gestor;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GestorResponseDTO {
    private UUID id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String email;
    private BigDecimal latitud;
    private BigDecimal longitud;
}