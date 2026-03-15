package com.medisync.medisync.controllers;

import com.medisync.medisync.application.dto.medicamento.MedicamentoRequestDTO;
import com.medisync.medisync.application.dto.medicamento.MedicamentoResponseDTO;
import com.medisync.medisync.application.usecases.medicamento.CrearMedicamentoUseCase;
import com.medisync.medisync.domain.entities.Medicamento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final CrearMedicamentoUseCase crearMedicamentoUseCase;

    public MedicamentoController(CrearMedicamentoUseCase crearMedicamentoUseCase) {
        this.crearMedicamentoUseCase = crearMedicamentoUseCase;
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
}
