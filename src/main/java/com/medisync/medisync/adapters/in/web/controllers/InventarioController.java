package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;
import java.util.UUID;

import com.medisync.medisync.application.usecases.inventario.ObtenerInventarioPorGestorUseCase;
import com.medisync.medisync.application.usecases.inventario.ObtenerInventariosUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medisync.medisync.adapters.in.web.dto.inventario.InventarioResponseDTO;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final ObtenerInventariosUseCase obtenerInventariosUseCase;
    private final ObtenerInventarioPorGestorUseCase obtenerInventarioPorGestorIdUseCase;

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
}