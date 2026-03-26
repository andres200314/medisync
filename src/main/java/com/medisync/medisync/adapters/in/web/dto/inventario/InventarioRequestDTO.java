package com.medisync.medisync.adapters.in.web.dto.inventario;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioRequestDTO {

    private UUID gestorId;

    private List<ItemInventarioDTO> items;
}