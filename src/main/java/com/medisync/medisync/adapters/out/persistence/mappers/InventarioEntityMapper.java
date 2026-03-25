package com.medisync.medisync.adapters.out.persistence.mappers;

import org.springframework.stereotype.Component;

import com.medisync.medisync.adapters.out.persistence.entities.InventarioEntity;
import com.medisync.medisync.domain.models.Inventario;

@Component
public class InventarioEntityMapper {

    private final MedicamentoEntityMapper medicamentoEntityMapper;
    private final GestorEntityMapper gestorEntityMapper;

    public InventarioEntityMapper(MedicamentoEntityMapper medicamentoEntityMapper,
                                   GestorEntityMapper gestorEntityMapper) {
        this.medicamentoEntityMapper = medicamentoEntityMapper;
        this.gestorEntityMapper = gestorEntityMapper;
    }

    public Inventario toDomain(InventarioEntity entity) {
        return Inventario.builder()
                .id(entity.getId())
                .medicamento(medicamentoEntityMapper.toDomain(entity.getMedicamento()))
                .gestor(gestorEntityMapper.toDomain(entity.getGestor()))
                .cantidad(entity.getCantidad())
                .precioUnitario(entity.getPrecioUnitario())
                .build();
    }

    public InventarioEntity toEntity(Inventario inventario) {
        return InventarioEntity.builder()
                .id(inventario.getId())
                .medicamento(medicamentoEntityMapper.toEntity(inventario.getMedicamento()))
                .gestor(gestorEntityMapper.toEntity(inventario.getGestor()))
                .cantidad(inventario.getCantidad())
                .precioUnitario(inventario.getPrecioUnitario())
                .build();
    }
}