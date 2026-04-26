package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;
import java.util.UUID;

import com.medisync.medisync.application.usecases.gestor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medisync.medisync.adapters.in.web.dto.gestor.GestorRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.gestor.GestorResponseDTO;

@RestController
@RequestMapping("/api/gestores")
@RequiredArgsConstructor
public class GestorController {

    private final CrearGestorUseCase crearGestorUseCase;
    private final ObtenerGestoresUseCase obtenerGestoresUseCase;
    private final ObtenerGestorPorIdUseCase obtenerGestorPorIdUseCase;
    private final ActualizarGestorUseCase actualizarGestorUseCase;
    private final EliminarGestorUseCase eliminarGestorUseCase;

    @PostMapping
    public ResponseEntity<GestorResponseDTO> crear(@RequestBody GestorRequestDTO request) {

        var gestor = crearGestorUseCase.ejecutar(
                request.nombre(),
                request.nit(),
                request.direccion(),
                request.telefono(),
                request.email(),
                request.password(),
                request.latitud(),
                request.longitud()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GestorResponseDTO.from(gestor));
    }

    @GetMapping
    public ResponseEntity<List<GestorResponseDTO>> obtenerTodos() {

        var gestores = obtenerGestoresUseCase.ejecutar().stream()
                .map(GestorResponseDTO::from)
                .toList();

        return ResponseEntity.ok(gestores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GestorResponseDTO> obtenerPorId(@PathVariable UUID id) {

        var gestor = obtenerGestorPorIdUseCase.ejecutar(id);

        return ResponseEntity.ok(GestorResponseDTO.from(gestor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GestorResponseDTO> actualizar(
            @PathVariable UUID id,
            @RequestBody GestorRequestDTO request) {

        var gestor = actualizarGestorUseCase.ejecutar(
                id,
                request.nombre(),
                request.direccion(),
                request.telefono(),
                request.email(),
                request.latitud(),
                request.longitud()
        );

        return ResponseEntity.ok(GestorResponseDTO.from(gestor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {

        eliminarGestorUseCase.ejecutar(id);

        return ResponseEntity.noContent().build();
    }
}