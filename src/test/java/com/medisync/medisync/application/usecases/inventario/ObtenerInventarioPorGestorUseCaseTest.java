package com.medisync.medisync.application.usecases.inventario;

import com.medisync.medisync.domain.enums.EstadoGestor;
import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;
import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IInventarioRepository;
import com.medisync.medisync.domain.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObtenerInventarioPorGestorUseCaseTest {

    @Mock
    private IInventarioRepository inventarioRepository;

    @InjectMocks
    private ObtenerInventarioPorGestorUseCase obtenerInventarioPorGestorUseCase;

    @Test
    void deberiaRetornarInventarioCuandoGestorTieneInventario() {
        // ARRANGE
        UUID gestorId = UUID.randomUUID();
        UUID medicamentoId = UUID.randomUUID();

        // Crear gestor
        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(Nombre.of("Farmacia Central"))
                .estado(EstadoGestor.ACTIVO)
                .build();

        // Crear medicamento
        Medicamento ibuprofeno = Medicamento.builder()
                .id(medicamentoId)
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .build();

        // Crear item de inventario
        ItemInventario item = new ItemInventario(
                ibuprofeno,
                Cantidad.of(50),
                Precio.of(BigDecimal.valueOf(12500.00))
        );

        // Crear inventario
        Inventario inventario = Inventario.builder()
                .id(UUID.randomUUID())
                .gestor(gestor)
                .items(List.of(item))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        // ACT
        Inventario resultado = obtenerInventarioPorGestorUseCase.ejecutar(gestorId);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(gestorId, resultado.getGestor().getId());
        assertEquals(1, resultado.getItems().size());
        assertEquals("Ibuprofeno", resultado.getItems().getFirst().medicamento().getNombre());
        assertEquals(50, resultado.getItems().getFirst().cantidad().valor());
        assertEquals(0, BigDecimal.valueOf(12500.00)
                .compareTo(resultado.getItems().getFirst().precioUnitario().valor()));

        verify(inventarioRepository, times(1)).findByGestorId(gestorId);
    }

    @Test
    void deberiaLanzarExcepcionCuandoGestorNoTieneInventario() {
        // ARRANGE
        UUID gestorId = UUID.randomUUID();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        BusinessRuleViolationException exception = assertThrows(
                BusinessRuleViolationException.class,
                () -> obtenerInventarioPorGestorUseCase.ejecutar(gestorId)
        );

        assertEquals("El gestor no tiene inventario", exception.getMessage());
        verify(inventarioRepository, times(1)).findByGestorId(gestorId);
    }

    @Test
    void deberiaLanzarExcepcionCuandoGestorIdEsNulo() {
        // ACT & ASSERT
        BusinessRuleViolationException exception = assertThrows(
                BusinessRuleViolationException.class,
                () -> obtenerInventarioPorGestorUseCase.ejecutar(null)
        );

        assertEquals("El ID del gestor no puede ser nulo", exception.getMessage());
        verify(inventarioRepository, never()).findByGestorId(any());
    }
}