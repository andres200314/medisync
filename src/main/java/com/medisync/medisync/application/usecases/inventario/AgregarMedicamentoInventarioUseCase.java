package com.medisync.medisync.application.usecases.inventario;

import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IInventarioRepository;
import com.medisync.medisync.domain.valueobjects.Cantidad;
import com.medisync.medisync.domain.valueobjects.Precio;

import java.util.UUID;

public class AgregarMedicamentoInventarioUseCase {

    private final IInventarioRepository inventarioRepository;

    public AgregarMedicamentoInventarioUseCase(IInventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario ejecutar(
            UUID gestorId,
            Medicamento medicamento,
            Cantidad cantidad,
            Precio precio
    ) {

        Inventario inventario = inventarioRepository.findByGestorId(gestorId)
                .orElseThrow(() -> new BusinessRuleViolationException("El gestor no tiene inventario"));

        inventario.agregarMedicamento(medicamento, cantidad, precio);

        return inventarioRepository.save(inventario);
    }
}
