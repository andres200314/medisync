package com.medisync.medisync.application.usecases.medicamento;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medisync.medisync.domain.entities.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;

@Service
public class ObtenerMedicamentosUseCase {
    private final IMedicamentoRepository medicamentoRepository;

    public ObtenerMedicamentosUseCase(IMedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> ejecutar() {
        return medicamentoRepository.findAll();
    }
}