package com.medisync.medisync.application.usecases.inventario;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.medisync.medisync.domain.valueobjects.Nombre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.models.Medicamento;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

@ExtendWith(MockitoExtension.class)
class ObtenerInventariosUseCaseTest {

    @Mock
    private IInventarioRepository inventarioRepository;

    @InjectMocks
    private ObtenerInventariosUseCase obtenerInventariosUseCase;

    @Test
    void deberiaRetornarListaDeInventarios() {
        // ARRANGE
        List<Inventario> inventarios = List.of(
                Inventario.builder()
                        .id(UUID.randomUUID())
                        .medicamento(Medicamento.builder().id(UUID.randomUUID()).nombre("Ibuprofeno").build())
                        .gestor(Gestor.builder().id(UUID.randomUUID()).nombre(new Nombre("Farmacia Central")).build())
                        .cantidad(50)
                        .precioUnitario(new BigDecimal("12500.00"))
                        .build(),
                Inventario.builder()
                        .id(UUID.randomUUID())
                        .medicamento(Medicamento.builder().id(UUID.randomUUID()).nombre("Amoxicilina").build())
                        .gestor(Gestor.builder().id(UUID.randomUUID()).nombre(new Nombre("Droguería San José")).build())
                        .cantidad(30)
                        .precioUnitario(new BigDecimal("8500.00"))
                        .build()
        );

        when(inventarioRepository.findAll()).thenReturn(inventarios);

        // ACT
        List<Inventario> resultado = obtenerInventariosUseCase.ejecutar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ibuprofeno", resultado.get(0).getMedicamento().getNombre());
        assertEquals("Droguería San José", resultado.get(1).getGestor().getNombre().valor());
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    void deberiaRetornarListaVaciaSiNoHayInventarios() {
        // ARRANGE
        when(inventarioRepository.findAll()).thenReturn(List.of());

        // ACT
        List<Inventario> resultado = obtenerInventariosUseCase.ejecutar();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    void deberiaMantenerPropiedadesDeCadaInventario() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        Inventario inventario = Inventario.builder()
                .id(id)
                .medicamento(Medicamento.builder().id(UUID.randomUUID()).nombre("Ibuprofeno").build())
                .gestor(Gestor.builder().id(UUID.randomUUID()).nombre(new Nombre("Farmacia Central")).build())
                .cantidad(50)
                .precioUnitario(new BigDecimal("12500.00"))
                .build();

        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));

        // ACT
        List<Inventario> resultado = obtenerInventariosUseCase.ejecutar();

        // ASSERT
        assertEquals(id, resultado.getFirst().getId());
        assertEquals(50, resultado.getFirst().getCantidad());
        assertEquals(new BigDecimal("12500.00"), resultado.getFirst().getPrecioUnitario());
        assertEquals("Ibuprofeno", resultado.getFirst().getMedicamento().getNombre());
        assertEquals("Farmacia Central", resultado.getFirst().getGestor().getNombre().valor());
    }
}