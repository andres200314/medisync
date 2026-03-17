package com.medisync.medisync.controllers;

import java.util.List;

import com.medisync.medisync.application.mappers.MedicamentoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medisync.medisync.application.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.application.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.application.usecases.medicamento.CrearMedicamentoUseCase;
import com.medisync.medisync.application.usecases.medicamento.ObtenerMedicamentosUseCase;
import com.medisync.medisync.domain.entities.Medicamento;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final CrearMedicamentoUseCase crearMedicamentoUseCase;
    private final ObtenerMedicamentosUseCase obtenerMedicamentosUseCase;
    private final MedicamentoMapper mapper;

    public MedicamentoController(MedicamentoMapper mapper, CrearMedicamentoUseCase crearMedicamentoUseCase, ObtenerMedicamentosUseCase obtenerMedicamentosUseCase) {
        this.crearMedicamentoUseCase = crearMedicamentoUseCase;
        this.obtenerMedicamentosUseCase = obtenerMedicamentosUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> crear(@RequestBody MedicamentoRequestDTO request) {
        Medicamento creado = crearMedicamentoUseCase.ejecutar(mapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creado));
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> obtenerTodos() {
        List<Medicamento> medicamentos = obtenerMedicamentosUseCase.ejecutar();
        return ResponseEntity.ok(medicamentos.stream().map(mapper::toResponse).toList());
    }
}