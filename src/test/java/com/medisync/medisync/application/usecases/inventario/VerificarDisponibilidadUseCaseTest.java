package com.medisync.medisync.application.usecases.inventario;

import com.medisync.medisync.domain.enums.EstadoGestor;
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
class VerificarDisponibilidadUseCaseTest {

    @Mock
    private IInventarioRepository inventarioRepository;

    @InjectMocks
    private VerificarDisponibilidadUseCase verificarDisponibilidadUseCase;

    @Test
    void deberiaRetornarTrueCuandoMedicamentoTieneStock() {
        // ARRANGE
        UUID gestorId = UUID.randomUUID();
        UUID medicamentoId = UUID.randomUUID();

        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(Nombre.of("Farmacia Central"))
                .estado(EstadoGestor.ACTIVO)
                .build();

        Medicamento ibuprofeno = Medicamento.builder()
                .id(medicamentoId)
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .build();

        ItemInventario item = new ItemInventario(
                ibuprofeno,
                Cantidad.of(50),
                Precio.of(BigDecimal.valueOf(12500.00))
        );

        Inventario inventario = Inventario.builder()
                .id(UUID.randomUUID())
                .gestor(gestor)
                .items(List.of(item))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        // ACT
        boolean resultado = verificarDisponibilidadUseCase.ejecutar(gestorId, medicamentoId);

        // ASSERT
        assertTrue(resultado);
        verify(inventarioRepository, times(1)).findByGestorId(gestorId);
    }

    @Test
    void deberiaRetornarFalseCuandoMedicamentoNoTieneStock() {
        // ARRANGE
        UUID gestorId = UUID.randomUUID();
        UUID medicamentoId = UUID.randomUUID();

        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(Nombre.of("Farmacia Central"))
                .estado(EstadoGestor.ACTIVO)
                .build();

        Medicamento ibuprofeno = Medicamento.builder()
                .id(medicamentoId)
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .build();

        ItemInventario item = new ItemInventario(
                ibuprofeno,
                Cantidad.of(0),
                Precio.of(BigDecimal.valueOf(12500.00))
        );

        Inventario inventario = Inventario.builder()
                .id(UUID.randomUUID())
                .gestor(gestor)
                .items(List.of(item))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        // ACT
        boolean resultado = verificarDisponibilidadUseCase.ejecutar(gestorId, medicamentoId);

        // ASSERT
        assertFalse(resultado);
        verify(inventarioRepository, times(1)).findByGestorId(gestorId);
    }

    @Test
    void deberiaRetornarFalseCuandoMedicamentoNoExisteEnInventario() {
        // ARRANGE
        UUID gestorId = UUID.randomUUID();
        UUID medicamentoExistenteId = UUID.randomUUID();
        UUID medicamentoNoExistenteId = UUID.randomUUID();

        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(Nombre.of("Farmacia Central"))
                .estado(EstadoGestor.ACTIVO)
                .build();

        Medicamento ibuprofeno = Medicamento.builder()
                .id(medicamentoExistenteId)
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .build();

        ItemInventario item = new ItemInventario(
                ibuprofeno,
                Cantidad.of(50),
                Precio.of(BigDecimal.valueOf(12500.00))
        );

        Inventario inventario = Inventario.builder()
                .id(UUID.randomUUID())
                .gestor(gestor)
                .items(List.of(item))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        // ACT
        boolean resultado = verificarDisponibilidadUseCase.ejecutar(gestorId, medicamentoNoExistenteId);

        // ASSERT
        assertFalse(resultado);
        verify(inventarioRepository, times(1)).findByGestorId(gestorId);
    }

    @Test
    void deberiaRetornarFalseCuandoGestorNoTieneInventario() {
        // ARRANGE
        UUID gestorId = UUID.randomUUID();
        UUID medicamentoId = UUID.randomUUID();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.empty());

        // ACT
        boolean resultado = verificarDisponibilidadUseCase.ejecutar(gestorId, medicamentoId);

        // ASSERT
        assertFalse(resultado);
        verify(inventarioRepository, times(1)).findByGestorId(gestorId);
    }

    @Test
    void deberiaRetornarFalseCuandoGestorIdEsNulo() {
        // ACT
        boolean resultado = verificarDisponibilidadUseCase.ejecutar(null, UUID.randomUUID());

        // ASSERT
        assertFalse(resultado);
        verify(inventarioRepository, never()).findByGestorId(any());
    }

    @Test
    void deberiaRetornarFalseCuandoMedicamentoIdEsNulo() {
        // ACT
        boolean resultado = verificarDisponibilidadUseCase.ejecutar(UUID.randomUUID(), null);

        // ASSERT
        assertFalse(resultado);
        verify(inventarioRepository, never()).findByGestorId(any());
    }
}