package com.medisync.medisync.adapters.in.web.mappers;

import org.springframework.stereotype.Component;

import com.medisync.medisync.adapters.in.web.dto.inventario.InventarioRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.inventario.InventarioResponseDTO;
import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;

@Component
public class InventarioMapper {

    public Inventario toEntity(InventarioRequestDTO dto) {
        return Inventario.builder()
                .medicamento(Medicamento.builder().id(dto.getMedicamentoId()).build())
                .gestor(Gestor.builder().id(dto.getGestorId()).build())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .build();
    }

    public InventarioResponseDTO toResponse(Inventario inventario) {
        return InventarioResponseDTO.builder()
                .id(inventario.getId())
                .medicamentoId(inventario.getMedicamento().getId())
                .medicamentoNombre(inventario.getMedicamento().getNombre())
                .gestorId(inventario.getGestor().getId())
                .gestorNombre(inventario.getGestor().getNombre())
                .cantidad(inventario.getCantidad())
                .precioUnitario(inventario.getPrecioUnitario())
                .build();
    }
}