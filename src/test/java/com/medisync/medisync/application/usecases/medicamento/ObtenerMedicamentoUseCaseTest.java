package com.medisync.medisync.application.usecases.medicamento;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medisync.medisync.domain.entities.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;

@ExtendWith(MockitoExtension.class)
class ObtenerMedicamentosUseCaseTest {

    @Mock
    private IMedicamentoRepository medicamentoRepository;

    @InjectMocks
    private ObtenerMedicamentosUseCase obtenerMedicamentosUseCase;

    @Test
    void deberiaRetornarListaDeMedicamentos() {
        // ARRANGE
        List<Medicamento> medicamentos = List.of(
                Medicamento.builder()
                        .id(UUID.randomUUID())
                        .nombre("Ibuprofeno")
                        .requiereFormula(false)
                        .descripcion("Antiinflamatorio y analgésico")
                        .build(),
                Medicamento.builder()
                        .id(UUID.randomUUID())
                        .nombre("Amoxicilina")
                        .requiereFormula(true)
                        .descripcion("Antibiótico")
                        .build()
        );

        when(medicamentoRepository.findAll()).thenReturn(medicamentos);

        // ACT
        List<Medicamento> resultado = obtenerMedicamentosUseCase.ejecutar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ibuprofeno", resultado.get(0).getNombre());
        assertEquals("Amoxicilina", resultado.get(1).getNombre());
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    void deberiaRetornarListaVaciaSiNoHayMedicamentos() {
        // ARRANGE
        when(medicamentoRepository.findAll()).thenReturn(List.of());

        // ACT
        List<Medicamento> resultado = obtenerMedicamentosUseCase.ejecutar();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    void deberiaMantenerPropiedadesDeCadaMedicamento() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        Medicamento medicamento = Medicamento.builder()
                .id(id)
                .nombre("Amoxicilina")
                .requiereFormula(true)
                .descripcion("Antibiótico")
                .build();

        when(medicamentoRepository.findAll()).thenReturn(List.of(medicamento));

        // ACT
        List<Medicamento> resultado = obtenerMedicamentosUseCase.ejecutar();

        // ASSERT
        assertEquals(id, resultado.get(0).getId());
        assertEquals("Amoxicilina", resultado.get(0).getNombre());
        assertTrue(resultado.get(0).getRequiereFormula());
        assertEquals("Antibiótico", resultado.get(0).getDescripcion());
    }
}