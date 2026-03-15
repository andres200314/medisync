package com.medisync.medisync.application.dto.medicamento;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicamentoRequestDTO {
    private String nombre;
    private Boolean requiereFormula;
    private String descripcion;
}
