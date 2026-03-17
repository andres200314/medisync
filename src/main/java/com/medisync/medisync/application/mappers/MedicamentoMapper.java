package com.medisync.medisync.application.mappers;


import com.medisync.medisync.application.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.application.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.domain.entities.Medicamento;
import org.springframework.stereotype.Component;

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
}