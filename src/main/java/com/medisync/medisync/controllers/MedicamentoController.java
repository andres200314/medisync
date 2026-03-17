package com.medisync.medisync.controllers;

import java.util.List;

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

    public MedicamentoController(CrearMedicamentoUseCase crearMedicamentoUseCase,
                                  ObtenerMedicamentosUseCase obtenerMedicamentosUseCase) {
        this.crearMedicamentoUseCase = crearMedicamentoUseCase;
        this.obtenerMedicamentosUseCase = obtenerMedicamentosUseCase;
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> crear(@RequestBody MedicamentoRequestDTO request) {
        Medicamento medicamento = Medicamento.builder()
                .nombre(request.getNombre())
                .requiereFormula(request.getRequiereFormula())
                .descripcion(request.getDescripcion())
                .build();

        Medicamento creado = crearMedicamentoUseCase.ejecutar(medicamento);

        MedicamentoResponseDTO response = MedicamentoResponseDTO.builder()
                .id(creado.getId())
                .nombre(creado.getNombre())
                .requiereFormula(creado.getRequiereFormula())
                .descripcion(creado.getDescripcion())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> obtenerTodos() {
        List<Medicamento> medicamentos = obtenerMedicamentosUseCase.ejecutar();

        List<MedicamentoResponseDTO> response = medicamentos.stream()
                .map(m -> MedicamentoResponseDTO.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .requiereFormula(m.getRequiereFormula())
                        .descripcion(m.getDescripcion())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }
}