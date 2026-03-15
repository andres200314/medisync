package com.medisync.medisync.application.usecases.medicamento;

import com.medisync.medisync.domain.entities.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;
import org.springframework.stereotype.Service;

// CrearMedicamentoUseCase.java
@Service
public class CrearMedicamentoUseCase {
    private final IMedicamentoRepository medicamentoRepository;

    public CrearMedicamentoUseCase(IMedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento ejecutar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }
}
