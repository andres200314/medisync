package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
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

import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.adapters.in.web.mappers.MedicamentoMapper;
import com.medisync.medisync.application.usecases.medicamento.ActualizarMedicamentoUseCase;
import com.medisync.medisync.application.usecases.medicamento.CrearMedicamentoUseCase;
import com.medisync.medisync.application.usecases.medicamento.EliminarMedicamentoUseCase;
import com.medisync.medisync.application.usecases.medicamento.ObtenerMedicamentoPorIdUseCase;
import com.medisync.medisync.application.usecases.medicamento.ObtenerMedicamentosUseCase;
import com.medisync.medisync.domain.models.Medicamento;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    private final CrearMedicamentoUseCase crearMedicamentoUseCase;
    private final ObtenerMedicamentosUseCase obtenerMedicamentosUseCase;
    private final ObtenerMedicamentoPorIdUseCase obtenerMedicamentoPorIdUseCase;
    private final EliminarMedicamentoUseCase eliminarMedicamentoUseCase;
    private final ActualizarMedicamentoUseCase actualizarMedicamentoUseCase;
    private final MedicamentoMapper mapper;


    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> crear(@RequestBody MedicamentoRequestDTO request) {
        Medicamento creado = crearMedicamentoUseCase.ejecutar(mapper.toDomain(request));
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

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> actualizar(@PathVariable("id") UUID id, @RequestBody MedicamentoRequestDTO request) {
    Medicamento actualizado = actualizarMedicamentoUseCase.ejecutar(id, mapper.toDomain(request));
    return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

}