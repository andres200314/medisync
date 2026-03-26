package com.medisync.medisync.adapters.in.web.dto.inventario;


import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInventarioDTO {

    private UUID medicamentoId;
    private String medicamentoNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
