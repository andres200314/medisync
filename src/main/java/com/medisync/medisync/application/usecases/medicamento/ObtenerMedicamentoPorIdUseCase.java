package com.medisync.medisync.application.usecases.medicamento;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.medisync.medisync.application.exceptions.MedicamentoNotFoundException;
import com.medisync.medisync.domain.entities.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;

@Service
public class ObtenerMedicamentoPorIdUseCase {

    private final IMedicamentoRepository medicamentoRepository;

    public ObtenerMedicamentoPorIdUseCase(IMedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento ejecutar(UUID id) {
    return medicamentoRepository.findById(id)
            .orElseThrow(() -> new MedicamentoNotFoundException(id.toString()));
    }
}