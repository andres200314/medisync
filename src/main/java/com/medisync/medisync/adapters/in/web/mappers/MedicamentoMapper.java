package com.medisync.medisync.adapters.in.web.mappers;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.domain.entities.Medicamento;

@Component
public class MedicamentoMapper {

    public Medicamento toEntity(MedicamentoRequestDTO dto) {
        return Medicamento.builder()
                .nombre(dto.getNombre())
                .requiereFormula(dto.getRequiereFormula())
                .descripcion(dto.getDescripcion())
                .build();
    }

    public MedicamentoResponseDTO toResponse(Medicamento medicamento) {
        return MedicamentoResponseDTO.builder()
                .id(medicamento.getId())
                .nombre(medicamento.getNombre())
                .requiereFormula(medicamento.getRequiereFormula())
                .descripcion(medicamento.getDescripcion())
                .build();
    }

    public Medicamento toEntityConId(UUID id, MedicamentoRequestDTO dto) {
    return Medicamento.builder()
            .id(id)
            .nombre(dto.getNombre())
            .requiereFormula(dto.getRequiereFormula())
            .descripcion(dto.getDescripcion())
            .build();
}
}