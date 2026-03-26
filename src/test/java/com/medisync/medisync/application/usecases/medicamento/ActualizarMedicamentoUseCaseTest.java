package com.medisync.medisync.application.usecases.medicamento;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medisync.medisync.adapters.in.web.exceptions.MedicamentoNotFoundException;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;

@ExtendWith(MockitoExtension.class)
class ActualizarMedicamentoUseCaseTest {

    @Mock
    private IMedicamentoRepository medicamentoRepository;

    @InjectMocks
    private ActualizarMedicamentoUseCase actualizarMedicamentoUseCase;

    @Test
    void deberiaActualizarMedicamentoExitosamente() {
        // ARRANGE
        UUID id = UUID.randomUUID();

        Medicamento existente = Medicamento.builder()
                .id(id)
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .descripcion("Antiinflamatorio")
                .build();

        Medicamento actualizado = Medicamento.builder()
                .id(id)
                .nombre("Paracetamol")
                .requiereFormula(false)
                .descripcion("Analgésico y antipirético")
                .build();

        when(medicamentoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(actualizado);

        // ACT
        Medicamento resultado = actualizarMedicamentoUseCase.ejecutar(id, actualizado);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Paracetamol", resultado.getNombre());
        assertFalse(resultado.getRequiereFormula());
        assertEquals("Analgésico y antipirético", resultado.getDescripcion());
        verify(medicamentoRepository, times(1)).findById(id);
        verify(medicamentoRepository, times(1)).save(any(Medicamento.class));
    }

    @Test
    void deberiaLanzarExcepcionCuandoMedicamentoNoExiste() {
        // ARRANGE
        UUID id = UUID.randomUUID();

        Medicamento medicamento = Medicamento.builder()
                .nombre("Paracetamol")
                .requiereFormula(false)
                .descripcion("Analgésico y antipirético")
                .build();

        when(medicamentoRepository.findById(id)).thenReturn(Optional.empty());

        // ACT & ASSERT
        MedicamentoNotFoundException ex = assertThrows(
                MedicamentoNotFoundException.class,
                () -> actualizarMedicamentoUseCase.ejecutar(id, medicamento)
        );

        assertEquals("Medicamento no encontrado con id: " + id, ex.getMessage());
        verify(medicamentoRepository, times(1)).findById(id);
        verify(medicamentoRepository, never()).save(any(Medicamento.class));
    }

    @Test
    void deberiaMantenerElMismoIdAlActualizar() {
        // ARRANGE
        UUID id = UUID.randomUUID();

        Medicamento existente = Medicamento.builder()
                .id(id)
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .descripcion("Antiinflamatorio")
                .build();

        Medicamento nuevaData = Medicamento.builder()
                .nombre("Amoxicilina")
                .requiereFormula(true)
                .descripcion("Antibiótico")
                .build();

        Medicamento guardado = Medicamento.builder()
                .id(id)
                .nombre("Amoxicilina")
                .requiereFormula(true)
                .descripcion("Antibiótico")
                .build();

        when(medicamentoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(guardado);

        // ACT
        Medicamento resultado = actualizarMedicamentoUseCase.ejecutar(id, nuevaData);

        // ASSERT
        assertEquals(id, resultado.getId());
        assertEquals("Amoxicilina", resultado.getNombre());
        assertTrue(resultado.getRequiereFormula());
    }
}
