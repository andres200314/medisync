package com.medisync.medisync.application.usecases.medicamento;

import com.medisync.medisync.domain.exceptions.MedicamentoNotFoundException;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


public class EliminarMedicamentoUseCase {
    private final IMedicamentoRepository medicamentoRepository;

    public EliminarMedicamentoUseCase (IMedicamentoRepository medicamentoRepository){
        this.medicamentoRepository = medicamentoRepository;
    }

    public void ejecutar(UUID id) {
        medicamentoRepository.findById(id)
                .orElseThrow(() -> new MedicamentoNotFoundException(id.toString()));
        medicamentoRepository.deleteById(id);
    }



}
