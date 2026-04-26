package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medisync.medisync.adapters.in.web.dto.gestor.GestorRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.gestor.GestorResponseDTO;
import com.medisync.medisync.application.usecases.gestor.ActualizarGestorUseCase;
import com.medisync.medisync.application.usecases.gestor.EliminarGestorUseCase;
import com.medisync.medisync.application.usecases.gestor.ObtenerGestorPorIdUseCase;
import com.medisync.medisync.application.usecases.gestor.ObtenerGestoresUseCase;
import com.medisync.medisync.application.usecases.gestor.RegistrarGestorUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gestores")
@RequiredArgsConstructor
public class GestorController {

    private final RegistrarGestorUseCase registrarGestorUseCase;
    private final ObtenerGestoresUseCase obtenerGestoresUseCase;
    private final ObtenerGestorPorIdUseCase obtenerGestorPorIdUseCase;
    private final ActualizarGestorUseCase actualizarGestorUseCase;
    private final EliminarGestorUseCase eliminarGestorUseCase;

    @PostMapping
    public ResponseEntity<GestorResponseDTO> crear(@RequestBody GestorRequestDTO request) {
        var gestor = registrarGestorUseCase.ejecutar(
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