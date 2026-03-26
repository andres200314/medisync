package com.medisync.medisync.application.usecases.medicamento;

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

import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;

@ExtendWith(MockitoExtension.class)
class CrearMedicamentoUseCaseTest {

    @Mock
    private IMedicamentoRepository medicamentoRepository;

    @InjectMocks
    private CrearMedicamentoUseCase crearMedicamentoUseCase;

    @Test
    void deberiaCrearMedicamentoExitosamente() {
        // ARRANGE
        Medicamento medicamento = Medicamento.builder()
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .descripcion("Antiinflamatorio y analgésico")
                .build();
        Medicamento medicamentoGuardado = Medicamento.builder()
                .id(UUID.randomUUID())
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .descripcion("Antiinflamatorio y analgésico")
                .build();
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamentoGuardado);

        // ACT
        Medicamento resultado = crearMedicamentoUseCase.ejecutar(medicamento);

        // ASSERT
        assertNotNull(resultado.getId());
        assertEquals("Ibuprofeno", resultado.getNombre());
        assertFalse(resultado.getRequiereFormula());
        verify(medicamentoRepository, times(1)).save(any(Medicamento.class));
    }

    @Test
    void deberiaMantenerPropiedadesCorrectamente() {
        // ARRANGE
        Medicamento medicamento = Medicamento.builder()
                .nombre("Amoxicilina")
                .requiereFormula(true)
                .descripcion("Antibiótico de amplio espectro para infecciones bacterianas")
                .build();
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamento);

        // ACT
        Medicamento resultado = crearMedicamentoUseCase.ejecutar(medicamento);

        // ASSERT
        assertEquals("Amoxicilina", resultado.getNombre());
        assertTrue(resultado.getRequiereFormula());
        assertEquals("Antibiótico de amplio espectro para infecciones bacterianas", resultado.getDescripcion());
    }

    @Test
    void deberiaLanzarExcepcionSiNombreEsVacio() {
        // ARRANGE
        Medicamento medicamento = Medicamento.builder()
                .nombre("")
                .requiereFormula(false)
                .descripcion("Antiinflamatorio")
                .build();

        // ACT & ASSERT
        assertThrows(BusinessRuleViolationException.class,
                () -> crearMedicamentoUseCase.ejecutar(medicamento));
        verify(medicamentoRepository, never()).save(any());
    }

    @Test
    void deberiaLanzarExcepcionSiDescripcionEsInsuficienteConFormula() {
        // ARRANGE
        Medicamento medicamento = Medicamento.builder()
                .nombre("Amoxicilina")
                .requiereFormula(true)
                .descripcion("Antibiótico")
                .build();

        // ACT & ASSERT
        assertThrows(BusinessRuleViolationException.class,
                () -> crearMedicamentoUseCase.ejecutar(medicamento));
        verify(medicamentoRepository, never()).save(any());
    }
}