package com.medisync.medisync.application.usecases.inventario;


import com.medisync.medisync.domain.enums.EstadoGestor;
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
class AgregarMedicamentoInventarioUseCaseTest {

    @Mock
    private IInventarioRepository inventarioRepository;

    @InjectMocks
    private AgregarMedicamentoInventarioUseCase useCase;

    @Test
    void deberiaAgregarNuevoMedicamento() {
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

        Inventario inventario = Inventario.builder()
                .gestor(gestor)
                .items(new ArrayList<>())
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(inventario))
                .thenReturn(inventario);

        Cantidad cantidad = Cantidad.of(10);
        Precio precio = Precio.of(BigDecimal.valueOf(5000));

        Inventario resultado = useCase.ejecutar(gestorId, medicamento, cantidad, precio);

        assertEquals(1, resultado.getItems().size());
        assertEquals(10, resultado.getItems().getFirst().cantidad().valor());

        verify(inventarioRepository).save(inventario);
    }

    @Test
    void deberiaSumarStockSiYaExiste() {
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

        ItemInventario itemExistente = new ItemInventario(
                medicamento,
                Cantidad.of(10),
                Precio.of(BigDecimal.valueOf(5000))
        );

        Inventario inventario = Inventario.builder()
                .gestor(gestor)
                .items(new ArrayList<>(java.util.List.of(itemExistente)))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(inventario))
                .thenReturn(inventario);

        Inventario resultado = useCase.ejecutar(
                gestorId,
                medicamento,
                Cantidad.of(5),
                Precio.of(BigDecimal.valueOf(5000))
        );

        assertEquals(1, resultado.getItems().size());
        assertEquals(15, resultado.getItems().getFirst().cantidad().valor());

        verify(inventarioRepository).save(inventario);
    }

    @Test
    void deberiaLanzarExcepcionSiInventarioNoExiste() {
        UUID gestorId = UUID.randomUUID();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                useCase.ejecutar(
                        gestorId,
                        Medicamento.builder().id(UUID.randomUUID()).build(),
                        Cantidad.of(10),
                        Precio.of(BigDecimal.valueOf(1000))
                )
        );
    }
}
