package com.medisync.medisync.application.usecases.inventario;

import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

import java.util.Optional;
import java.util.UUID;

public class CrearInventarioUseCase {

    private final IInventarioRepository inventarioRepository;

    public CrearInventarioUseCase(IInventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario ejecutar(Inventario inventario) {

        UUID gestorId = inventario.getGestor().getId();

        Optional<Inventario> existenteOpt = inventarioRepository.findByGestorId(gestorId);

        if (existenteOpt.isPresent()) {

            Inventario existente = existenteOpt.get();


            inventario.getItems().forEach(item ->
                    existente.agregarMedicamento(
                            item.medicamento(),
                            item.cantidad(),
                            item.precioUnitario()
                    )
            );

            return inventarioRepository.save(existente);
        }

        return inventarioRepository.save(inventario);
    }
}