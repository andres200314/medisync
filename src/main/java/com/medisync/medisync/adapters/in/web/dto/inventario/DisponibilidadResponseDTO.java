package com.medisync.medisync.adapters.in.web.dto.inventario;

import com.medisync.medisync.domain.models.Inventario;

import java.util.List;
import java.util.UUID;

public record DisponibilidadResponseDTO(
        UUID gestorId,
        String gestorNombre,
        List<ItemInventarioResponseDTO> items
) {
    public static DisponibilidadResponseDTO from(Inventario inventario, List<ItemInventarioResponseDTO> itemsFiltrados) {
        return new DisponibilidadResponseDTO(
                inventario.getGestor().getId(),
                inventario.getGestor().getNombre().valor(),
                itemsFiltrados
        );
    }
}