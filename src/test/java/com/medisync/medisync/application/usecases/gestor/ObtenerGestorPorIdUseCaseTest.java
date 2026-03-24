package com.medisync.medisync.application.usecases.gestor;


import java.util.Optional;
import java.util.UUID;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.medisync.medisync.domain.exceptions.GestorNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.repositories.IGestorRepository;
@ExtendWith(MockitoExtension.class)
class ObtenerGestorPorIdUseCaseTest {

    @Mock
    private IGestorRepository gestorRepository;

    @InjectMocks
    private ObtenerGestorPorIdUseCase obtenerGestorPorIdUseCase;

    @Test
    void deberiaObtenerGestorExitosamente() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        Gestor gestor = Gestor.builder()
                .id(id)
                .nombre("Farmacia Central")
                .nit("9001234666-7")
                .email("farmacia@gmail.com")
                .passwordHash("hashedPassword")
                .build();

        when(gestorRepository.findById(id)).thenReturn(Optional.of(gestor));

        // ACT
        Gestor resultado = obtenerGestorPorIdUseCase.ejecutar(id);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Farmacia Central", resultado.getNombre());
        verify(gestorRepository, times(1)).findById(id);
    }

    @Test
    void deberiaLanzarExcepcionSiNoExiste() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        when(gestorRepository.findById(id)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(GestorNotFoundException.class, () -> obtenerGestorPorIdUseCase.ejecutar(id));

        verify(gestorRepository, times(1)).findById(id);
    }
}