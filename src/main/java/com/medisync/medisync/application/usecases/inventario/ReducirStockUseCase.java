package com.medisync.medisync.application.usecases.inventario;

import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IInventarioRepository;
import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;

import java.util.UUID;

public class ReducirStockUseCase {

    private final IInventarioRepository inventarioRepository;

    public ReducirStockUseCase(IInventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario ejecutar(UUID gestorId, Medicamento medicamento, int cantidad) {

        Inventario inventario = inventarioRepository.findByGestorId(gestorId)
                .orElseThrow(() -> new BusinessRuleViolationException("Inventario no encontrado"));

        inventario.reducirStock(medicamento, cantidad);

        return inventarioRepository.save(inventario);
    }
}