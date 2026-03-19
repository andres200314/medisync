package com.medisync.medisync.adapters.in.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medisync.medisync.adapters.in.web.dto.gestor.GestorRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.gestor.GestorResponseDTO;
import com.medisync.medisync.adapters.in.web.mappers.GestorMapper;
import com.medisync.medisync.application.usecases.gestor.CrearGestorUseCase;
import com.medisync.medisync.domain.entities.Gestor;

@RestController
@RequestMapping("/api/gestores")
public class GestorController {

    private final CrearGestorUseCase crearGestorUseCase;
    
    private final GestorMapper mapper;

    public GestorController(GestorMapper mapper, CrearGestorUseCase crearGestorUseCase)
                                  {
        this.crearGestorUseCase = crearGestorUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<GestorResponseDTO> crear(@RequestBody GestorRequestDTO request) {
        Gestor creado = crearGestorUseCase.ejecutar(mapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }


}