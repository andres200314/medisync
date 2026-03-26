package com.medisync.medisync.adapters.out.persistence.mappers;

import com.medisync.medisync.adapters.out.persistence.entities.InventarioEntity;
import com.medisync.medisync.adapters.out.persistence.entities.InventarioMedicamentoEntity;
import com.medisync.medisync.adapters.out.persistence.entities.MedicamentoEntity;
import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.valueobjects.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventarioEntityMapper {

    private final GestorEntityMapper gestorMapper;


    public Inventario toDomain(InventarioEntity entity) {
        if (entity == null) {
            return null;
        }


        Gestor gestor = gestorMapper.toDomain(entity.getGestor());

        // Convertir items
        List<ItemInventario> items = entity.getItems().stream()
                .map(this::toItemInventario)
                .collect(Collectors.toList());

        return Inventario.builder()
                .id(entity.getId())
                .gestor(gestor)
                .items(items)
                .build();
    }

    public InventarioEntity toEntity(Inventario inventario) {
        if (inventario == null) {
            return null;
        }

        InventarioEntity entity = InventarioEntity.builder()
                .id(inventario.getId())
                .gestor(gestorMapper.toEntity(inventario.getGestor()))
                .build();


        List<InventarioMedicamentoEntity> itemsEntity = inventario.getItems().stream()
                .map(item -> toInventarioMedicamentoEntity(item, entity))
                .collect(Collectors.toList());

        entity.setItems(itemsEntity);

        return entity;
    }


    private InventarioMedicamentoEntity toInventarioMedicamentoEntity(ItemInventario item, InventarioEntity inventarioEntity) {
        MedicamentoEntity medicamentoEntity = MedicamentoEntity.builder()
                .id(item.medicamento().getId())
                .build();

        return InventarioMedicamentoEntity.builder()
                .id(item.medicamento().getId())
                .inventario(inventarioEntity)
                .medicamento(medicamentoEntity)
                .cantidad(item.cantidad().valor())
                .precioUnitario(item.precioUnitario().valor())
                .build();
    }


    private ItemInventario toItemInventario(InventarioMedicamentoEntity entity) {
        // Convertir medicamento (solo datos básicos para el dominio)
        Medicamento medicamento = Medicamento.builder()
                .id(entity.getMedicamento().getId())
                .nombre(entity.getMedicamento().getNombre())
                .requiereFormula(entity.getMedicamento().getRequiereFormula())
                .descripcion(entity.getMedicamento().getDescripcion())
                .build();

        Cantidad cantidad = Cantidad.of(entity.getCantidad());
        Precio precio = Precio.of(entity.getPrecioUnitario());

        return new ItemInventario(medicamento, cantidad, precio);
    }
}