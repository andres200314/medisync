package com.medisync.medisync.domain.models;

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
public class Inventario {
    private UUID id;
    private Medicamento medicamento;
    private Gestor gestor;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    public void validar() {
        if (cantidad == null || cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if (precioUnitario == null || precioUnitario.signum() < 0) {
            throw new IllegalArgumentException("El precio unitario no puede ser negativo");
        }
    }
}