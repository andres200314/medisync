package com.medisync.medisync.application.usecases.inventario;

import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

import java.util.Optional;
import java.util.UUID;

public class VerificarDisponibilidadUseCase {

    private final IInventarioRepository inventarioRepository;

    public VerificarDisponibilidadUseCase(IInventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public boolean ejecutar(UUID gestorId, UUID medicamentoId) {
        if (gestorId == null || medicamentoId == null) {
            return false;
        }

        Medicamento medicamento = Medicamento.builder()
                .id(medicamentoId)
                .build();

        Optional<Inventario> inventario = inventarioRepository.findByGestorId(gestorId);

        return inventario.map(med -> med.tieneStock(medicamento)).orElse(false);

    }
}