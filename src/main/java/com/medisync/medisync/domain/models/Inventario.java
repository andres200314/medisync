package com.medisync.medisync.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {


    private UUID id;
    private Medicamento medicamento;
    private Gestor gestor;
    private Integer cantidad;
    private BigDecimal precioUnitario;


}
