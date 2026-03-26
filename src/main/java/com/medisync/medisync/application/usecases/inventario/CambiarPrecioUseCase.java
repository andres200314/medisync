package com.medisync.medisync.application.usecases.inventario;

import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IInventarioRepository;
import com.medisync.medisync.domain.valueobjects.Precio;
import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;

import java.util.UUID;

public class CambiarPrecioUseCase {

    private final IInventarioRepository inventarioRepository;

    public CambiarPrecioUseCase(IInventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario ejecutar(UUID gestorId, Medicamento medicamento, Precio nuevoPrecio) {

        Inventario inventario = inventarioRepository.findByGestorId(gestorId)
                .orElseThrow(() -> new BusinessRuleViolationException("Inventario no encontrado"));

        inventario.cambiarPrecio(medicamento, nuevoPrecio);

        return inventarioRepository.save(inventario);
    }
}
