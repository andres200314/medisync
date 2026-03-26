package com.medisync.medisync.domain.models;

import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;
import com.medisync.medisync.domain.valueobjects.Cantidad;
import com.medisync.medisync.domain.valueobjects.ItemInventario;
import com.medisync.medisync.domain.valueobjects.Precio;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    private UUID id;
    private Gestor gestor;

    @Builder.Default
    private List<ItemInventario> items = new ArrayList<>();


    public void agregarMedicamento(Medicamento medicamento, Cantidad cantidad, Precio precio) {

        validarGestorActivo();

        if (medicamento == null) {
            throw new BusinessRuleViolationException("El medicamento no puede ser nulo");
        }

        ItemInventario existente = buscarItem(medicamento);

        if (existente != null) {
            ItemInventario actualizado = existente.aumentarStock(cantidad.valor());
            actualizarItem(existente, actualizado);
        } else {
            items.add(new ItemInventario(medicamento, cantidad, precio));
        }
    }

    public void reducirStock(Medicamento medicamento, int cantidad) {

        validarGestorActivo();

        ItemInventario existente = buscarItem(medicamento);

        if (existente == null) {
            throw new BusinessRuleViolationException("El medicamento no existe en el inventario");
        }

        ItemInventario actualizado = existente.reducirStock(cantidad);
        actualizarItem(existente, actualizado);
    }

    public void cambiarPrecio(Medicamento medicamento, Precio nuevoPrecio) {

        validarGestorActivo();

        ItemInventario existente = buscarItem(medicamento);

        if (existente == null) {
            throw new BusinessRuleViolationException("El medicamento no existe en el inventario");
        }

        ItemInventario actualizado = existente.cambiarPrecio(nuevoPrecio);
        actualizarItem(existente, actualizado);
    }



    public boolean tieneStock(Medicamento medicamento) {
        ItemInventario existente = buscarItem(medicamento);
        return existente != null && existente.tieneStock();
    }

    public boolean estaDisponibleParaUsuarios() {
        return gestor.esVisibleParaUsuarios() && tieneAlMenosUnItemConStock();
    }



    private void validarGestorActivo() {
        if (gestor == null) {
            throw new BusinessRuleViolationException("El inventario debe tener un gestor");
        }

        if (!gestor.estaActivo()) {
            throw new BusinessRuleViolationException("El gestor debe estar activo para modificar el inventario");
        }
    }




    private ItemInventario buscarItem(Medicamento medicamento) {

        for (ItemInventario item : items) {
            if (item.esDelMismoMedicamento(medicamento)) {
                return item;
            }
        }

        return null;
    }

    private void actualizarItem(ItemInventario original, ItemInventario actualizado) {

        int index = items.indexOf(original);

        if (index == -1) {
            throw new IllegalStateException("Error interno: item no encontrado");
        }

        items.set(index, actualizado);
    }

    private boolean tieneAlMenosUnItemConStock() {

        for (ItemInventario item : items) {
            if (item.tieneStock()) {
                return true;
            }
        }

        return false;
    }
}