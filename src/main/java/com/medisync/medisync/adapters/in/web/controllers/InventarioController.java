package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medisync.medisync.adapters.in.web.dto.inventario.InventarioResponseDTO;
import com.medisync.medisync.adapters.in.web.mappers.InventarioMapper;
import com.medisync.medisync.application.usecases.inventario.ObtenerInventariosUseCase;
import com.medisync.medisync.domain.models.Inventario;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final ObtenerInventariosUseCase obtenerInventariosUseCase;
    private final InventarioMapper mapper;


    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> obtenerTodos() {
        List<Inventario> inventarios = obtenerInventariosUseCase.ejecutar();
        return ResponseEntity.ok(inventarios.stream().map(mapper::toResponse).toList());
    }
}