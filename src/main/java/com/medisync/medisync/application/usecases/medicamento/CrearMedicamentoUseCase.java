package com.medisync.medisync.application.usecases.medicamento;


import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;

// CrearMedicamentoUseCase.java

public class CrearMedicamentoUseCase {
    private final IMedicamentoRepository medicamentoRepository;

    public CrearMedicamentoUseCase(IMedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento ejecutar(Medicamento medicamento) {
        medicamento.normalizarNombre();
        medicamento.validar();
        return medicamentoRepository.save(medicamento);
    }
}
