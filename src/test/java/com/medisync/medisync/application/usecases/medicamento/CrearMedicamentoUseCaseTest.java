package com.medisync.medisync.application.usecases.medicamento;

import com.medisync.medisync.domain.entities.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        when(medicamentoRepository.save(medicamento)).thenReturn(medicamentoGuardado);

        // ACT
        Medicamento resultado = crearMedicamentoUseCase.ejecutar(medicamento);

        // ASSERT
        assertNotNull(resultado.getId());
        assertEquals("Ibuprofeno", resultado.getNombre());
        assertFalse(resultado.getRequiereFormula());
        verify(medicamentoRepository, times(1)).save(medicamento);
    }

    @Test
    void deberiaMantenerPropiedadesCorrectamente() {
        // ARRANGE
        Medicamento medicamento = Medicamento.builder()
                .nombre("Amoxicilina")
                .requiereFormula(true)
                .descripcion("Antibiótico")
                .build();

        when(medicamentoRepository.save(medicamento)).thenReturn(medicamento);

        // ACT
        Medicamento resultado = crearMedicamentoUseCase.ejecutar(medicamento);

        // ASSERT
        assertEquals("Amoxicilina", resultado.getNombre());
        assertTrue(resultado.getRequiereFormula());
        assertEquals("Antibiótico", resultado.getDescripcion());
    }
}