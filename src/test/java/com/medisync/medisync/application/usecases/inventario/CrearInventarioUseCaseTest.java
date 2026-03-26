package com.medisync.medisync.application.usecases.inventario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medisync.medisync.domain.enums.EstadoGestor;
import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;
import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IInventarioRepository;
import com.medisync.medisync.domain.valueobjects.*;

@ExtendWith(MockitoExtension.class)
class CrearInventarioUseCaseTest {

    @Mock
    private IInventarioRepository inventarioRepository;

    @InjectMocks
    private CrearInventarioUseCase crearInventarioUseCase;

    @Test
    void deberiaCrearInventarioConItemsExitosamente() {
        // ARRANGE
        UUID medicamentoId = UUID.randomUUID();
        UUID gestorId = UUID.randomUUID();

        // Crear Value Objects
        Nombre nombreGestor = Nombre.of("Farmacia Central");
        Cantidad cantidad = Cantidad.of(50);
        Precio precio = Precio.of(BigDecimal.valueOf(12500.00));

        // Crear gestor con estado ACTIVO
        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(nombreGestor)
                .estado(EstadoGestor.ACTIVO)  // ← IMPORTANTE
                .build();

        Medicamento medicamento = Medicamento.builder()
                .id(medicamentoId)
                .nombre("Ibuprofeno")
                .requiereFormula(false)
                .build();

        // Crear item de inventario
        ItemInventario item = new ItemInventario(medicamento, cantidad, precio);

        // Crear inventario con items
        Inventario inventario = Inventario.builder()
                .gestor(gestor)
                .items(List.of(item))
                .build();

        // Inventario guardado (con ID generado)
        Inventario inventarioGuardado = Inventario.builder()
                .id(UUID.randomUUID())
                .gestor(gestor)
                .items(List.of(item))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.empty());
        when(inventarioRepository.save(any(Inventario.class)))
                .thenReturn(inventarioGuardado);

        // ACT
        Inventario resultado = crearInventarioUseCase.ejecutar(inventario);

        // ASSERT
        assertNotNull(resultado.getId());
        assertEquals(1, resultado.getItems().size());
        assertEquals(50, resultado.getItems().getFirst().cantidad().valor());
        assertEquals("Ibuprofeno", resultado.getItems().getFirst().medicamento().getNombre());
        assertEquals("Farmacia Central", resultado.getGestor().getNombre().valor());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    void deberiaAgregarItemsAlInventarioExistente() {
        // ARRANGE
        UUID medicamentoId = UUID.randomUUID();
        UUID gestorId = UUID.randomUUID();
        UUID inventarioId = UUID.randomUUID();

        // Value Objects
        Nombre nombreGestor = Nombre.of("Farmacia Central");
        Cantidad cantidadExistente = Cantidad.of(50);
        Cantidad cantidadNueva = Cantidad.of(30);
        Precio precio = Precio.of(BigDecimal.valueOf(12500.00));

        // Crear gestor con estado ACTIVO
        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(nombreGestor)
                .estado(EstadoGestor.ACTIVO)  // ← IMPORTANTE
                .build();

        // Medicamento existente
        Medicamento medicamento = Medicamento.builder()
                .id(medicamentoId)
                .nombre("Ibuprofeno")
                .build();

        // Inventario existente
        ItemInventario itemExistente = new ItemInventario(medicamento, cantidadExistente, precio);
        Inventario inventarioExistente = Inventario.builder()
                .id(inventarioId)
                .gestor(gestor)
                .items(new ArrayList<>(List.of(itemExistente)))
                .build();

        // Nuevo inventario con el item a agregar (mismo medicamento)
        ItemInventario nuevoItem = new ItemInventario(medicamento, cantidadNueva, precio);
        Inventario inventarioNuevo = Inventario.builder()
                .gestor(gestor)
                .items(List.of(nuevoItem))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventarioExistente));
        when(inventarioRepository.save(inventarioExistente))
                .thenReturn(inventarioExistente);

        // ACT
        Inventario resultado = crearInventarioUseCase.ejecutar(inventarioNuevo);

        // ASSERT
        assertEquals(1, resultado.getItems().size());
        assertEquals(80, resultado.getItems().getFirst().cantidad().valor()); // 50 + 30 = 80
        verify(inventarioRepository, times(1)).save(inventarioExistente);
    }

    @Test
    void deberiaAgregarNuevoMedicamentoAlInventarioExistente() {
        // ARRANGE
        UUID medicamentoId1 = UUID.randomUUID();
        UUID medicamentoId2 = UUID.randomUUID();
        UUID gestorId = UUID.randomUUID();
        UUID inventarioId = UUID.randomUUID();

        // Value Objects
        Nombre nombreGestor = Nombre.of("Farmacia Central");
        Cantidad cantidadExistente = Cantidad.of(50);
        Cantidad cantidadNueva = Cantidad.of(30);
        Precio precio = Precio.of(BigDecimal.valueOf(12500.00));

        // Crear gestor con estado ACTIVO
        Gestor gestor = Gestor.builder()
                .id(gestorId)
                .nombre(nombreGestor)
                .estado(EstadoGestor.ACTIVO)  // ← IMPORTANTE
                .build();

        // Medicamentos
        Medicamento medicamento1 = Medicamento.builder()
                .id(medicamentoId1)
                .nombre("Ibuprofeno")
                .build();
        Medicamento medicamento2 = Medicamento.builder()
                .id(medicamentoId2)
                .nombre("Paracetamol")
                .build();

        // Inventario existente con un medicamento
        ItemInventario itemExistente = new ItemInventario(medicamento1, cantidadExistente, precio);
        Inventario inventarioExistente = Inventario.builder()
                .id(inventarioId)
                .gestor(gestor)
                .items(new ArrayList<>(List.of(itemExistente)))
                .build();

        // Nuevo inventario con el nuevo medicamento
        ItemInventario nuevoItem = new ItemInventario(medicamento2, cantidadNueva, precio);
        Inventario inventarioNuevo = Inventario.builder()
                .gestor(gestor)
                .items(List.of(nuevoItem))
                .build();

        when(inventarioRepository.findByGestorId(gestorId))
                .thenReturn(Optional.of(inventarioExistente));
        when(inventarioRepository.save(inventarioExistente))
                .thenReturn(inventarioExistente);

        // ACT
        Inventario resultado = crearInventarioUseCase.ejecutar(inventarioNuevo);

        // ASSERT
        assertEquals(2, resultado.getItems().size());
        assertEquals(50, resultado.getItems().get(0).cantidad().valor());
        assertEquals(30, resultado.getItems().get(1).cantidad().valor());
        verify(inventarioRepository, times(1)).save(inventarioExistente);
    }

    @Test
    void deberiaLanzarExcepcionSiCantidadEsNegativa() {
        // ARRANGE & ACT & ASSERT
        assertThrows(BusinessRuleViolationException.class, () -> Cantidad.of(-10));
    }

    @Test
    void deberiaLanzarExcepcionSiPrecioEsNegativo() {
        // ARRANGE & ACT & ASSERT
        assertThrows(BusinessRuleViolationException.class, () -> Precio.of(BigDecimal.valueOf(-100.00)));
    }


}