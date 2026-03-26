package com.medisync.medisync.adapters.in.web.mappers;


import com.medisync.medisync.adapters.in.web.dto.inventario.InventarioResponseDTO;
import com.medisync.medisync.adapters.in.web.dto.inventario.ItemInventarioDTO;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.valueobjects.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventarioMapper {

    public InventarioResponseDTO toResponse(Inventario inventario) {
        List<ItemInventarioDTO> itemsDTO = inventario.getItems().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());


        return InventarioResponseDTO.builder()
                .id(inventario.getId())
                .gestorId(inventario.getGestor().getId())
                .gestorNombre(inventario.getGestor().getNombre().valor())
                .items(itemsDTO)
                .build();
    }

    private ItemInventarioDTO toItemDTO(ItemInventario item) {
        BigDecimal subtotal = item.precioUnitario().valor()
                .multiply(BigDecimal.valueOf(item.cantidad().valor()));

        return ItemInventarioDTO.builder()
                .medicamentoId(item.medicamento().getId())
                .medicamentoNombre(item.medicamento().getNombre())
                .cantidad(item.cantidad().valor())
                .precioUnitario(item.precioUnitario().valor())
                .build();
    }
}