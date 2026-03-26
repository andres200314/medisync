package com.medisync.medisync.adapters.in.web.dto.inventario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioResponseDTO {
    private UUID id;
    private UUID gestorId;
    private String gestorNombre;
    private List<ItemInventarioDTO> items;
}