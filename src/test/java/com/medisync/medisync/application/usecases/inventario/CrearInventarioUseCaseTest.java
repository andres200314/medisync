package com.medisync.medisync.application.usecases.inventario;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

@ExtendWith(MockitoExtension.class)
class CrearInventarioUseCaseTest {

    @Mock
    private IInventarioRepository inventarioRepository;

    @InjectMocks
    private CrearInventarioUseCase crearInventarioUseCase;

    @Test
    void deberiaCrearInventarioExitosamente() {
        // ARRANGE
        UUID medicamentoId = UUID.randomUUID();
        UUID gestorId = UUID.randomUUID();

        Inventario inventario = Inventario.builder()
                .medicamento(Medicamento.builder().id(medicamentoId).build())
                .gestor(Gestor.builder().id(gestorId).build())
                .cantidad(50)
                .precioUnitario(new BigDecimal("12500.00"))
                .build();

        Inventario inventarioGuardado = Inventario.builder()
                .id(UUID.randomUUID())
                .medicamento(Medicamento.builder().id(medicamentoId).nombre("Ibuprofeno").build())
                .gestor(Gestor.builder().id(gestorId).nombre("Farmacia Central").build())
                .cantidad(50)
                .precioUnitario(new BigDecimal("12500.00"))
                .build();

        when(inventarioRepository.findByMedicamentoIdAndGestorId(medicamentoId, gestorId))
                .thenReturn(List.of());
        when(inventarioRepository.save(inventario)).thenReturn(inventarioGuardado);

        // ACT
        Inventario resultado = crearInventarioUseCase.ejecutar(inventario);

        // ASSERT
        assertNotNull(resultado.getId());
        assertEquals(50, resultado.getCantidad());
        assertEquals("Ibuprofeno", resultado.getMedicamento().getNombre());
        assertEquals("Farmacia Central", resultado.getGestor().getNombre());
        verify(inventarioRepository, times(1)).save(inventario);
    }

    @Test
    void deberiaSumarCantidadSiInventarioYaExiste() {
        // ARRANGE
        UUID medicamentoId = UUID.randomUUID();
        UUID gestorId = UUID.randomUUID();
        UUID inventarioId = UUID.randomUUID();

        Inventario inventarioNuevo = Inventario.builder()
                .medicamento(Medicamento.builder().id(medicamentoId).build())
                .gestor(Gestor.builder().id(gestorId).build())
                .cantidad(30)
                .precioUnitario(new BigDecimal("12500.00"))
                .build();

        Inventario inventarioExistente = Inventario.builder()
                .id(inventarioId)
                .medicamento(Medicamento.builder().id(medicamentoId).build())
                .gestor(Gestor.builder().id(gestorId).build())
                .cantidad(50)
                .precioUnitario(new BigDecimal("12500.00"))
                .build();

        Inventario inventarioActualizado = Inventario.builder()
                .id(inventarioId)
                .medicamento(Medicamento.builder().id(medicamentoId).build())
                .gestor(Gestor.builder().id(gestorId).build())
                .cantidad(80)
                .precioUnitario(new BigDecimal("12500.00"))
                .build();

        when(inventarioRepository.findByMedicamentoIdAndGestorId(medicamentoId, gestorId))
                .thenReturn(List.of(inventarioExistente));
        when(inventarioRepository.save(inventarioExistente)).thenReturn(inventarioActualizado);

        // ACT
        Inventario resultado = crearInventarioUseCase.ejecutar(inventarioNuevo);

        // ASSERT
        assertEquals(80, resultado.getCantidad());
        verify(inventarioRepository, times(1)).save(inventarioExistente);
    }

    @Test
    void deberiaLanzarExcepcionSiCantidadEsNegativa() {
        // ARRANGE
        Inventario inventario = Inventario.builder()
                .medicamento(Medicamento.builder().id(UUID.randomUUID()).build())
                .gestor(Gestor.builder().id(UUID.randomUUID()).build())
                .cantidad(-10)
                .precioUnitario(new BigDecimal("12500.00"))
                .build();

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class,
                () -> crearInventarioUseCase.ejecutar(inventario));
        verify(inventarioRepository, never()).save(any());
    }

    @Test
    void deberiaLanzarExcepcionSiPrecioEsNegativo() {
        // ARRANGE
        Inventario inventario = Inventario.builder()
                .medicamento(Medicamento.builder().id(UUID.randomUUID()).build())
                .gestor(Gestor.builder().id(UUID.randomUUID()).build())
                .cantidad(50)
                .precioUnitario(new BigDecimal("-100.00"))
                .build();

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class,
                () -> crearInventarioUseCase.ejecutar(inventario));
        verify(inventarioRepository, never()).save(any());
    }
}