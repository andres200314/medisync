package com.medisync.medisync.application.usecases.medicamento;

import java.util.UUID;



import com.medisync.medisync.domain.exceptions.MedicamentoNotFoundException;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;


public class ActualizarMedicamentoUseCase {

    private final IMedicamentoRepository medicamentoRepository;

    public ActualizarMedicamentoUseCase(IMedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento ejecutar(UUID id, Medicamento medicamento) {
        medicamentoRepository.findById(id)
                .orElseThrow(() -> new MedicamentoNotFoundException(id.toString()));

        Medicamento actualizado = Medicamento.builder()
                .id(id)
                .nombre(medicamento.getNombre())
                .requiereFormula(medicamento.getRequiereFormula())
                .descripcion(medicamento.getDescripcion())
                .build();

        return medicamentoRepository.save(actualizado);
    }
}