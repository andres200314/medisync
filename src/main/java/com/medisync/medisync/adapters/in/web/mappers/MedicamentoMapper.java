package com.medisync.medisync.adapters.in.web.mappers;

import org.springframework.stereotype.Component;

import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.domain.models.Medicamento;

@Component
public class MedicamentoMapper {

    public Medicamento toDomain(MedicamentoRequestDTO dto) {
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

}