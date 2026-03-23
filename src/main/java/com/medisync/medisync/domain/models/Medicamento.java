package com.medisync.medisync.domain.models;


import lombok.*;


import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicamento {
    private UUID id;
    private String nombre;
    private Boolean requiereFormula;
    private String descripcion;
}
