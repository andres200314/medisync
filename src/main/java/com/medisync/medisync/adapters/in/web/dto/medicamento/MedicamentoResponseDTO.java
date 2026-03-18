package com.medisync.medisync.adapters.in.web.dto.medicamento;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicamentoResponseDTO {
    private UUID id;
    private String nombre;
    private Boolean requiereFormula;
    private String descripcion;
}
