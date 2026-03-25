package com.medisync.medisync.adapters.in.web.dto.inventario;

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
public class InventarioResponseDTO {
    private UUID id;
    private UUID medicamentoId;
    private String medicamentoNombre;
    private UUID gestorId;
    private String gestorNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}