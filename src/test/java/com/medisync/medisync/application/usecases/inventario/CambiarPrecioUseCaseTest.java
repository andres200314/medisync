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
class CambiarPrecioUseCaseTest {

    @Mock
    private IInventarioRepository inventarioRepository;

    @InjectMocks
    private CambiarPrecioUseCase useCase;

    @Test
    void deberiaCambiarPrecioExitosamente() {
        UUID gestorId = UUID.randomUUID();
        UUID medicamentoId = UUID.randomUUID();

        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(Nombre.of("Farmacia"))
                .estado(EstadoGestor.ACTIVO)
                .build();

        Medicamento medicamento = Medicamento.builder()
                .id(medicamentoId)
                .nombre("Ibuprofeno")
                .build();

        ItemInventario item = new ItemInventario(
                medicamento,
                Cantidad.of(10),
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

        Precio nuevoPrecio = Precio.of(BigDecimal.valueOf(8000));

        Inventario resultado = useCase.ejecutar(gestorId, medicamento, nuevoPrecio);

        assertEquals(8000, resultado.getItems().getFirst().precioUnitario().valor().intValue());

        verify(inventarioRepository).save(inventario);
    }

    @Test
    void deberiaLanzarExcepcionSiInventarioNoExiste() {
        UUID gestorId = UUID.randomUUID();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.empty());

        assertThrows(BusinessRuleViolationException.class, () ->
                useCase.ejecutar(
                        gestorId,
                        Medicamento.builder().id(UUID.randomUUID()).build(),
                        Precio.of(BigDecimal.valueOf(5000))
                )
        );
    }

    @Test
    void deberiaLanzarExcepcionSiMedicamentoNoExiste() {
        UUID gestorId = UUID.randomUUID();

        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(Nombre.of("Farmacia"))
                .estado(EstadoGestor.ACTIVO)
                .build();

        Inventario inventario = Inventario.builder()
                .gestor(gestor)
                .items(new ArrayList<>())
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        assertThrows(BusinessRuleViolationException.class, () ->
                useCase.ejecutar(
                        gestorId,
                        Medicamento.builder().id(UUID.randomUUID()).build(),
                        Precio.of(BigDecimal.valueOf(5000))
                )
        );
    }
}
