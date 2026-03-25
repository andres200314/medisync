package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medisync.medisync.adapters.in.web.dto.inventario.InventarioRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.inventario.InventarioResponseDTO;
import com.medisync.medisync.adapters.in.web.mappers.InventarioMapper;
import com.medisync.medisync.application.usecases.inventario.CrearInventarioUseCase;
import com.medisync.medisync.application.usecases.inventario.ObtenerInventariosUseCase;
import com.medisync.medisync.domain.models.Inventario;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final CrearInventarioUseCase crearInventarioUseCase;
    private final ObtenerInventariosUseCase obtenerInventariosUseCase;
    private final InventarioMapper mapper;

    public InventarioController(CrearInventarioUseCase crearInventarioUseCase,
                                 ObtenerInventariosUseCase obtenerInventariosUseCase,
                                 InventarioMapper mapper) {
        this.crearInventarioUseCase = crearInventarioUseCase;
        this.obtenerInventariosUseCase = obtenerInventariosUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> crear(@RequestBody InventarioRequestDTO request) {
        Inventario creado = crearInventarioUseCase.ejecutar(mapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> obtenerTodos() {
        List<Inventario> inventarios = obtenerInventariosUseCase.ejecutar();
        return ResponseEntity.ok(inventarios.stream().map(mapper::toResponse).toList());
    }
}