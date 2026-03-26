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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReducirStockUseCaseTest {

    @Mock
    private IInventarioRepository inventarioRepository;

    @InjectMocks
    private ReducirStockUseCase useCase;

    @Test
    void deberiaReducirStock() {
        UUID gestorId = UUID.randomUUID();

        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(Nombre.of("Farmacia"))
                .estado(EstadoGestor.ACTIVO)
                .build();

        Medicamento medicamento = Medicamento.builder()
                .id(UUID.randomUUID())
                .nombre("Ibuprofeno")
                .build();

        ItemInventario item = new ItemInventario(
                medicamento,
                Cantidad.of(20),
                Precio.of(BigDecimal.valueOf(5000))
        );

        Inventario inventario = Inventario.builder()
                .gestor(gestor)
                .items(new ArrayList<>(java.util.List.of(item)))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(inventario))
                .thenReturn(inventario);

        Inventario resultado = useCase.ejecutar(gestorId, medicamento, 5);

        assertEquals(15, resultado.getItems().getFirst().cantidad().valor());
        verify(inventarioRepository).save(inventario);
    }

    @Test
    void deberiaFallarPorStockInsuficiente() {
        UUID gestorId = UUID.randomUUID();

        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(Nombre.of("Farmacia"))
                .estado(EstadoGestor.ACTIVO)
                .build();

        Medicamento medicamento = Medicamento.builder()
                .id(UUID.randomUUID())
                .nombre("Ibuprofeno")
                .build();

        ItemInventario item = new ItemInventario(
                medicamento,
                Cantidad.of(5),
                Precio.of(BigDecimal.valueOf(5000))
        );

        Inventario inventario = Inventario.builder()
                .gestor(gestor)
                .items(new ArrayList<>(java.util.List.of(item)))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        assertThrows(BusinessRuleViolationException.class, () ->
                useCase.ejecutar(gestorId, medicamento, 10)
        );
    }

    @Test
    void deberiaFallarSiInventarioNoExiste() {
        UUID gestorId = UUID.randomUUID();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.empty());

        assertThrows(BusinessRuleViolationException.class, () ->
                useCase.ejecutar(
                        gestorId,
                        Medicamento.builder().id(UUID.randomUUID()).build(),
                        5
                )
        );
    }
}