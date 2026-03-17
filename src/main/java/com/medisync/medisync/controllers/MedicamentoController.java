package com.medisync.medisync.controllers;

import java.util.List;
import java.util.UUID;

import com.medisync.medisync.application.usecases.medicamento.EliminarMedicamentoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medisync.medisync.application.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.application.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.application.mappers.MedicamentoMapper;
import com.medisync.medisync.application.usecases.medicamento.CrearMedicamentoUseCase;
import com.medisync.medisync.application.usecases.medicamento.ObtenerMedicamentoPorIdUseCase;
import com.medisync.medisync.application.usecases.medicamento.ObtenerMedicamentosUseCase;
import com.medisync.medisync.domain.entities.Medicamento;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final CrearMedicamentoUseCase crearMedicamentoUseCase;
    private final ObtenerMedicamentosUseCase obtenerMedicamentosUseCase;
    private final ObtenerMedicamentoPorIdUseCase obtenerMedicamentoPorIdUseCase;
    private final EliminarMedicamentoUseCase eliminarMedicamentoUseCase;
    private final MedicamentoMapper mapper;

    public MedicamentoController(MedicamentoMapper mapper,
                                 CrearMedicamentoUseCase crearMedicamentoUseCase,
                                 ObtenerMedicamentosUseCase obtenerMedicamentosUseCase,
                                 ObtenerMedicamentoPorIdUseCase obtenerMedicamentoPorIdUseCase, EliminarMedicamentoUseCase eliminarMedicamentoUseCase) {
        this.crearMedicamentoUseCase = crearMedicamentoUseCase;
        this.obtenerMedicamentosUseCase = obtenerMedicamentosUseCase;
        this.obtenerMedicamentoPorIdUseCase = obtenerMedicamentoPorIdUseCase;
        this.mapper = mapper;
        this.eliminarMedicamentoUseCase = eliminarMedicamentoUseCase;
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

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> obtenerPorId(@PathVariable("id") UUID id) {
        Medicamento medicamento = obtenerMedicamentoPorIdUseCase.ejecutar(id);
        return ResponseEntity.ok(mapper.toResponse(medicamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        eliminarMedicamentoUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}