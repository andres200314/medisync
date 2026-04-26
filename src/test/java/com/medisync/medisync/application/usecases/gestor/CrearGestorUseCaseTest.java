package com.medisync.medisync.application.usecases.gestor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medisync.medisync.application.usecases.inventario.CrearInventarioUseCase;
import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.repositories.IGestorRepository;
import com.medisync.medisync.domain.services.IPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CrearGestorUseCaseTest {

    @Mock
    private IGestorRepository gestorRepository;

    @Mock
    private IPasswordEncoder passwordEncoder;

    @Mock
    private CrearInventarioUseCase crearInventarioUseCase;

    private CrearGestorUseCase crearGestorUseCase;

    private static final String NOMBRE = "Farmacia Central";
    private static final String NIT = "900123456-1";
    private static final String DIRECCION = "Calle 10 #20-30";
    private static final String TELEFONO = "3001234567";
    private static final String EMAIL = "farmacia@central.com";
    private static final String PASSWORD = "password123";
    private static final String HASHED_PASSWORD = "$2a$10$hasheado";
    private static final double LATITUD = 6.2442;
    private static final double LONGITUD = -75.5812;

    @BeforeEach
    void setUp() {
        crearGestorUseCase = new CrearGestorUseCase(gestorRepository, passwordEncoder, crearInventarioUseCase);
    }

    @Test
    void deberiaCrearGestorExitosamente() {
        // Given
        when(passwordEncoder.encode(PASSWORD)).thenReturn(HASHED_PASSWORD);

        Gestor gestorEsperado = Gestor.crear(NOMBRE, NIT, DIRECCION, TELEFONO, EMAIL, HASHED_PASSWORD, LATITUD, LONGITUD);
        Gestor gestorConId = Gestor.builder()
                .id(UUID.randomUUID())
                .nombre(gestorEsperado.getNombre())
                .nit(gestorEsperado.getNit())
                .direccion(gestorEsperado.getDireccion())
                .telefono(gestorEsperado.getTelefono())
                .email(gestorEsperado.getEmail())
                .passwordHash(gestorEsperado.getPasswordHash())
                .coordenadas(gestorEsperado.getCoordenadas())
                .estado(gestorEsperado.getEstado())
                .build();

        when(gestorRepository.save(any(Gestor.class))).thenReturn(gestorConId);
        when(crearInventarioUseCase.ejecutar(any(Inventario.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Gestor resultado = crearGestorUseCase.ejecutar(NOMBRE, NIT, DIRECCION, TELEFONO, EMAIL, PASSWORD, LATITUD, LONGITUD);

        // Then
        assertNotNull(resultado.getId());
        assertEquals(NOMBRE, resultado.getNombre().valor());
        assertEquals(NIT, resultado.getNit().valor());
        assertEquals(DIRECCION, resultado.getDireccion());
        assertEquals(TELEFONO, resultado.getTelefono().valor());
        assertEquals(EMAIL, resultado.getEmail().valor());
        assertEquals(HASHED_PASSWORD, resultado.getPasswordHash());

        verify(passwordEncoder).encode(PASSWORD);
        verify(gestorRepository).save(any(Gestor.class));
        verify(crearInventarioUseCase).ejecutar(any(Inventario.class));
    }
}