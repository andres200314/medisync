package com.medisync.medisync.application.usecases.gestor;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.repositories.IGestorRepository;
import com.medisync.medisync.domain.services.IPasswordEncoder;
import com.medisync.medisync.domain.valueobjects.Coordenadas;
import com.medisync.medisync.domain.valueobjects.Email;
import com.medisync.medisync.domain.valueobjects.Nit;
import com.medisync.medisync.domain.valueobjects.Nombre;
import com.medisync.medisync.domain.valueobjects.Telefono;

@ExtendWith(MockitoExtension.class)
class CrearGestorUseCaseTest {

    @Mock
    private IGestorRepository gestorRepository;

    @Mock
    private IPasswordEncoder passwordEncoder;

    @InjectMocks
    private CrearGestorUseCase crearGestorUseCase;

    @Test
    void deberiaCrearGestorExitosamente() {
        // ARRANGE
        Gestor gestor = Gestor.builder()
                .nombre(new Nombre("Farmacia Central"))
                .nit(new Nit("900123456-1"))
                .direccion("Calle 10 #20-30")
                .telefono(new Telefono("3001234567"))
                .email(new Email("farmacia@central.com"))
                .passwordHash("password123")
                .coordenadas(new Coordenadas(new BigDecimal("6.2442"), new BigDecimal("-75.5812")))
                .build();

        Gestor gestorGuardado = Gestor.builder()
                .id(UUID.randomUUID())
                .nombre(new Nombre("Farmacia Central"))
                .nit(new Nit("900123456-1"))
                .direccion("Calle 10 #20-30")
                .telefono(new Telefono("3001234567"))
                .email(new Email("farmacia@central.com"))
                .passwordHash("$2a$10$hasheado")
                .coordenadas(new Coordenadas(new BigDecimal("6.2442"), new BigDecimal("-75.5812")))
                .build();

        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$hasheado");
        when(gestorRepository.save(gestor)).thenReturn(gestorGuardado);

        // ACT
        Gestor resultado = crearGestorUseCase.ejecutar(gestor);

        // ASSERT
        assertNotNull(resultado.getId());
        assertEquals("Farmacia Central", resultado.getNombre().valor());
        assertEquals("$2a$10$hasheado", resultado.getPasswordHash());
        verify(passwordEncoder, times(1)).encode("password123");
        verify(gestorRepository, times(1)).save(gestor);
    }

    @Test
    void deberiaHashearPasswordAntesDeGuardar() {
        // ARRANGE
        Gestor gestor = Gestor.builder()
                .nombre(new Nombre("Droguería San José"))
                .nit(new Nit("800987654-2"))
                .direccion("Carrera 5 #15-20")
                .telefono(new Telefono("3109876543"))
                .email(new Email("drogueria@sanjose.com"))
                .passwordHash("miPassword")
                .coordenadas(new Coordenadas(new BigDecimal("6.2530"), new BigDecimal("-75.5743")))
                .build();

        when(passwordEncoder.encode("miPassword")).thenReturn("$2a$10$otroHash");
        when(gestorRepository.save(gestor)).thenReturn(gestor);

        // ACT
        crearGestorUseCase.ejecutar(gestor);

        // ASSERT
        assertEquals("$2a$10$otroHash", gestor.getPasswordHash());
        verify(passwordEncoder, times(1)).encode("miPassword");
        verify(gestorRepository, times(1)).save(gestor);
    }

    @Test
    void deberiaMantenerPropiedadesCorrectamente() {
        // ARRANGE
        Gestor gestor = Gestor.builder()
                .nombre(new Nombre("Droguería San José"))
                .nit(new Nit("800987654-2"))
                .direccion("Carrera 5 #15-20")
                .telefono(new Telefono("3109876543"))
                .email(new Email("drogueria@sanjose.com"))
                .passwordHash("miPassword")
                .coordenadas(new Coordenadas(new BigDecimal("6.2530"), new BigDecimal("-75.5743")))
                .build();

        when(passwordEncoder.encode(any())).thenReturn("$2a$10$otroHash");
        when(gestorRepository.save(gestor)).thenReturn(gestor);

        // ACT
        Gestor resultado = crearGestorUseCase.ejecutar(gestor);

        // ASSERT
        assertEquals("Droguería San José", resultado.getNombre().valor());
        assertEquals("800987654-2", resultado.getNit().valor());
        assertEquals("Carrera 5 #15-20", resultado.getDireccion());
        assertEquals("3109876543", resultado.getTelefono().valor());
        assertEquals("drogueria@sanjose.com", resultado.getEmail().valor());
        assertEquals(new BigDecimal("6.2530"), resultado.getCoordenadas().latitud());
        assertEquals(new BigDecimal("-75.5743"), resultado.getCoordenadas().longitud());
    }
}