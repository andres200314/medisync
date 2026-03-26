package com.medisync.medisync.application.usecases.inventario;

import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

import java.util.UUID;

public class ObtenerInventarioPorGestorUseCase {

    private final IInventarioRepository inventarioRepository;

    public ObtenerInventarioPorGestorUseCase(IInventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario ejecutar(UUID gestorId) {
        if (gestorId == null) {
            throw new BusinessRuleViolationException("El ID del gestor no puede ser nulo");
        }

        return inventarioRepository.findByGestorId(gestorId)
                .orElseThrow(() -> new BusinessRuleViolationException("El gestor no tiene inventario"));
    }
}