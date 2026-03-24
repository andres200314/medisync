package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;
import java.util.UUID;

import com.medisync.medisync.application.usecases.gestor.ObtenerGestorPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medisync.medisync.adapters.in.web.dto.gestor.GestorRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.gestor.GestorResponseDTO;
import com.medisync.medisync.adapters.in.web.mappers.GestorMapper;
import com.medisync.medisync.application.usecases.gestor.CrearGestorUseCase;
import com.medisync.medisync.application.usecases.gestor.ObtenerGestoresUseCase;
import com.medisync.medisync.domain.models.Gestor;

@RestController
@RequestMapping("/api/gestores")
@RequiredArgsConstructor
public class GestorController {

    private final CrearGestorUseCase crearGestorUseCase;
    private final ObtenerGestoresUseCase obtenerGestoresUseCase;
    private final ObtenerGestorPorIdUseCase obtenerGestorPorIdUseCase;
    private final GestorMapper mapper;

    @PostMapping
    public ResponseEntity<GestorResponseDTO> crear(@RequestBody GestorRequestDTO request) {
        Gestor creado = crearGestorUseCase.ejecutar(mapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @GetMapping
    public ResponseEntity<List<GestorResponseDTO>> obtenerTodos() {
        List<Gestor> gestores = obtenerGestoresUseCase.ejecutar();
        return ResponseEntity.ok(gestores.stream().map(mapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GestorResponseDTO> obtenerPorId(@PathVariable UUID id) {
        Gestor gestor = obtenerGestorPorIdUseCase.ejecutar(id);
        return ResponseEntity.ok(mapper.toResponse(gestor));
    }
}