package com.medisync.medisync.application.usecases.inventario;

import java.util.List;

import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

public class CrearInventarioUseCase {

    private final IInventarioRepository inventarioRepository;

    public CrearInventarioUseCase(IInventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario ejecutar(Inventario inventario) {
        inventario.validar();

        List<Inventario> existentes = inventarioRepository.findByMedicamentoIdAndGestorId(
            inventario.getMedicamento().getId(),
            inventario.getGestor().getId()
        );

        if (!existentes.isEmpty()) {
        Inventario inventarioExistente = existentes.get(0);
        inventarioExistente.setCantidad(inventarioExistente.getCantidad() + inventario.getCantidad());
        return inventarioRepository.save(inventarioExistente);
        }

        return inventarioRepository.save(inventario);
    }
}