package com.medisync.medisync.adapters.in.web.controllers;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.application.usecases.medicamento.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Medicamentos", description = "Gestión de medicamentos")
@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    private final CrearMedicamentoUseCase crearMedicamentoUseCase;
    private final ObtenerMedicamentosUseCase obtenerMedicamentosUseCase;
    private final ObtenerMedicamentoPorIdUseCase obtenerMedicamentoPorIdUseCase;
    private final EliminarMedicamentoUseCase eliminarMedicamentoUseCase;
    private final ActualizarMedicamentoUseCase actualizarMedicamentoUseCase;

    @Operation(summary = "Crear medicamento", description = "Crea un nuevo medicamento en el catálogo global")
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

    @Operation(summary = "Obtener todos los medicamentos", description = "Retorna el catálogo completo de medicamentos")
    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(
                obtenerMedicamentosUseCase.ejecutar().stream()
                        .map(MedicamentoResponseDTO::from)
                        .toList()
        );
    }

    @Operation(summary = "Obtener medicamento por ID", description = "Retorna un medicamento por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(MedicamentoResponseDTO.from(obtenerMedicamentoPorIdUseCase.ejecutar(id)));
    }

    @Operation(summary = "Actualizar medicamento", description = "Actualiza el nombre y descripción de un medicamento")
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

    @Operation(summary = "Eliminar medicamento", description = "Elimina un medicamento del catálogo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        eliminarMedicamentoUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}