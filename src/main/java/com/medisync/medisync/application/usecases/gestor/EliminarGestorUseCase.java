package com.medisync.medisync.application.usecases.gestor;


import java.util.UUID;

import com.medisync.medisync.adapters.in.web.exceptions.GestorNotFoundException;
import com.medisync.medisync.domain.repositories.IGestorRepository;

public class EliminarGestorUseCase {

    private final IGestorRepository gestorRepository;

    public EliminarGestorUseCase(IGestorRepository gestorRepository) {
        this.gestorRepository = gestorRepository;
    }

    public void ejecutar(UUID id) {
        gestorRepository.findById(id)
                .orElseThrow(() -> new GestorNotFoundException(id.toString()));
        gestorRepository.deleteById(id);
    }
}