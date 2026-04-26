package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.application.usecases.medicamento.*;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    private final CrearMedicamentoUseCase crearMedicamentoUseCase;
    private final ObtenerMedicamentosUseCase obtenerMedicamentosUseCase;
    private final ObtenerMedicamentoPorIdUseCase obtenerMedicamentoPorIdUseCase;
    private final EliminarMedicamentoUseCase eliminarMedicamentoUseCase;
    private final ActualizarMedicamentoUseCase actualizarMedicamentoUseCase;

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> crear(@RequestBody MedicamentoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                MedicamentoResponseDTO.from(
                        crearMedicamentoUseCase.ejecutar(
                                request.nombre(),
                                request.requiereFormula(),
                                request.descripcion()
                        )
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(
                obtenerMedicamentosUseCase.ejecutar().stream()
                        .map(MedicamentoResponseDTO::from)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(MedicamentoResponseDTO.from(obtenerMedicamentoPorIdUseCase.ejecutar(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> actualizar(
            @PathVariable UUID id,
            @RequestBody MedicamentoRequestDTO request) {
        return ResponseEntity.ok(
                MedicamentoResponseDTO.from(
                        actualizarMedicamentoUseCase.ejecutar(
                                id,
                                request.nombre(),
                                request.descripcion()
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        eliminarMedicamentoUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}