package com.medisync.medisync.adapters.in.web.controllers;

import com.medisync.medisync.adapters.in.web.dto.inventario.DisponibilidadResponseDTO;
import com.medisync.medisync.adapters.in.web.dto.inventario.InventarioResponseDTO;
import com.medisync.medisync.adapters.in.web.dto.inventario.ItemInventarioRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.inventario.ItemInventarioResponseDTO;
import com.medisync.medisync.application.usecases.inventario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final ObtenerInventariosUseCase obtenerInventariosUseCase;
    private final ObtenerInventarioPorGestorUseCase obtenerInventarioPorGestorIdUseCase;
    private final AgregarMedicamentoInventarioUseCase agregarMedicamentoInventarioUseCase;
    private final BuscarDisponibilidadMedicamentoUseCase buscarDisponibilidadMedicamentoUseCase;
    private final AjustarStockUseCase ajustarStockUseCase;
    private final EstablecerStockUseCase establecerStockUseCase;

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> obtenerTodos() {
        var inventarios = obtenerInventariosUseCase.ejecutar().stream()
                .map(InventarioResponseDTO::from)
                .toList();
        return ResponseEntity.ok(inventarios);
    }

    @GetMapping("/gestor/{gestorId}")
    public ResponseEntity<InventarioResponseDTO> obtenerPorGestorId(@PathVariable UUID gestorId) {
        var inventario = obtenerInventarioPorGestorIdUseCase.ejecutar(gestorId);
        return ResponseEntity.ok(InventarioResponseDTO.from(inventario));
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<List<DisponibilidadResponseDTO>> buscarDisponibilidad(
            @RequestParam String medicamento) {

        var medicamentos = buscarDisponibilidadMedicamentoUseCase.ejecutar(medicamento);

        var response = medicamentos.stream()
                .map(inventario -> new DisponibilidadResponseDTO(
                        inventario.getGestor().getId(),
                        inventario.getGestor().getNombre().valor(),
                        inventario.getItems().stream()
                                .filter(item -> item.medicamento().getNombre()
                                        .toLowerCase()
                                        .contains(medicamento.toLowerCase()))
                                .filter(item -> item.tieneStock())
                                .map(ItemInventarioResponseDTO::from)
                                .toList()
                ))
                .filter(dto -> !dto.items().isEmpty())
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/gestor/{gestorId}/medicamentos")
    public ResponseEntity<InventarioResponseDTO> agregarMedicamento(
            @PathVariable UUID gestorId,
            @RequestBody ItemInventarioRequestDTO request) {
        var inventario = agregarMedicamentoInventarioUseCase.ejecutar(
                gestorId,
                request.medicamentoId(),
                request.cantidad(),
                request.precioUnitario()
        );
        return ResponseEntity.ok(InventarioResponseDTO.from(inventario));
    }


    @PatchMapping("/gestor/{gestorId}/medicamentos/{medicamentoId}/stock/ajustar")
    public ResponseEntity<InventarioResponseDTO> ajustarStock(
            @PathVariable UUID gestorId,
            @PathVariable UUID medicamentoId,
            @RequestParam int cantidad) {
        var inventario = ajustarStockUseCase.ejecutar(gestorId, medicamentoId, cantidad);
        return ResponseEntity.ok(InventarioResponseDTO.from(inventario));
    }

    @PutMapping("/gestor/{gestorId}/medicamentos/{medicamentoId}/stock/establecer")
    public ResponseEntity<InventarioResponseDTO> establecerStock(
            @PathVariable UUID gestorId,
            @PathVariable UUID medicamentoId,
            @RequestParam int cantidad) {
        var inventario = establecerStockUseCase.ejecutar(gestorId, medicamentoId, cantidad);
        return ResponseEntity.ok(InventarioResponseDTO.from(inventario));
    }
}