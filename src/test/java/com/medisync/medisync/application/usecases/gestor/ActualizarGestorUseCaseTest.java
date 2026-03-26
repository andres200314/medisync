package com.medisync.medisync.application.usecases.gestor;

import java.math.BigDecimal;
import java.util.Optional;
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

import com.medisync.medisync.adapters.in.web.exceptions.GestorNotFoundException;
import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.repositories.IGestorRepository;
import com.medisync.medisync.domain.services.IPasswordEncoder;
import com.medisync.medisync.domain.valueobjects.Coordenadas;
import com.medisync.medisync.domain.valueobjects.Email;
import com.medisync.medisync.domain.valueobjects.Nit;
import com.medisync.medisync.domain.valueobjects.Nombre;
import com.medisync.medisync.domain.valueobjects.Telefono;

@ExtendWith(MockitoExtension.class)
class ActualizarGestorUseCaseTest {

    @Mock
    private IGestorRepository gestorRepository;

    @Mock
    private IPasswordEncoder passwordEncoder;

    @InjectMocks
    private ActualizarGestorUseCase actualizarGestorUseCase;

    @Test
    void deberiaActualizarGestorExitosamente() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        
        Nombre nombreOriginal = new Nombre("Juan Pérez");
        Nit nitOriginal = new Nit("123456789-0");
        Telefono telefonoOriginal = new Telefono("3001234567");
        Email emailOriginal = new Email("juan@example.com");
        Coordenadas coordenadasOriginal = new Coordenadas(
            new BigDecimal("4.60971"), 
            new BigDecimal("-74.08175")
        );
        
        Gestor existente = Gestor.builder()
                .id(id)
                .nombre(nombreOriginal)
                .nit(nitOriginal)
                .direccion("Calle 123")
                .telefono(telefonoOriginal)
                .email(emailOriginal)
                .passwordHash("hash_antiguo")
                .coordenadas(coordenadasOriginal)
                .build();
        
        Nombre nombreNuevo = new Nombre("Juan Carlos Pérez");
        Nit nitNuevo = new Nit("987654321-0");
        Telefono telefonoNuevo = new Telefono("3007654321");
        Email emailNuevo = new Email("juancarlos@example.com");
        Coordenadas coordenadasNuevas = new Coordenadas(
            new BigDecimal("4.60971"), 
            new BigDecimal("-74.08175")
        );
        String nuevaPassword = "nueva_password123";
        String hashEncodeado = "hash_encriptado";
        
        Gestor gestorActualizado = Gestor.builder()
                .id(id)
                .nombre(nombreNuevo)
                .nit(nitNuevo)
                .direccion("Carrera 45")
                .telefono(telefonoNuevo)
                .email(emailNuevo)
                .passwordHash(nuevaPassword)
                .coordenadas(coordenadasNuevas)
                .build();
        
        Gestor gestorGuardado = Gestor.builder()
                .id(id)
                .nombre(nombreNuevo)
                .nit(nitNuevo)
                .direccion("Carrera 45")
                .telefono(telefonoNuevo)
                .email(emailNuevo)
                .passwordHash(hashEncodeado)
                .coordenadas(coordenadasNuevas)
                .build();
        
        when(gestorRepository.findById(id)).thenReturn(Optional.of(existente));
        when(passwordEncoder.encode(nuevaPassword)).thenReturn(hashEncodeado);
        when(gestorRepository.save(any(Gestor.class))).thenReturn(gestorGuardado);
        
        // ACT
        Gestor resultado = actualizarGestorUseCase.ejecutar(id, gestorActualizado);
        
        // ASSERT
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(nombreNuevo.valor(), resultado.getNombre().valor());
        assertEquals(nitNuevo.valor(), resultado.getNit().valor());
        assertEquals("Carrera 45", resultado.getDireccion());
        assertEquals(telefonoNuevo.valor(), resultado.getTelefono().valor());
        assertEquals(emailNuevo.valor(), resultado.getEmail().valor());
        assertEquals(hashEncodeado, resultado.getPasswordHash());
        // Para record con BigDecimal, usamos .compareTo() para comparar valores
        assertEquals(0, coordenadasNuevas.latitud().compareTo(resultado.getCoordenadas().latitud()));
        assertEquals(0, coordenadasNuevas.longitud().compareTo(resultado.getCoordenadas().longitud()));
        
        verify(gestorRepository, times(1)).findById(id);
        verify(passwordEncoder, times(1)).encode(nuevaPassword);
        verify(gestorRepository, times(1)).save(any(Gestor.class));
    }
    
    @Test
    void deberiaLanzarExcepcionCuandoGestorNoExiste() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        
        Nombre nombre = new Nombre("Juan Pérez");
        Nit nit = new Nit("123456789-0");
        Telefono telefono = new Telefono("3001234567");
        Email email = new Email("juan@example.com");
        Coordenadas coordenadas = new Coordenadas(
            new BigDecimal("4.60971"), 
            new BigDecimal("-74.08175")
        );
        
        Gestor gestor = Gestor.builder()
                .nombre(nombre)
                .nit(nit)
                .direccion("Calle 123")
                .telefono(telefono)
                .email(email)
                .passwordHash("password123")
                .coordenadas(coordenadas)
                .build();
        
        when(gestorRepository.findById(id)).thenReturn(Optional.empty());
        
        // ACT & ASSERT
        GestorNotFoundException ex = assertThrows(
                GestorNotFoundException.class,
                () -> actualizarGestorUseCase.ejecutar(id, gestor)
        );
        
        assertEquals("Gestor no encontrado con id: " + id, ex.getMessage());
        verify(gestorRepository, times(1)).findById(id);
        verify(gestorRepository, never()).save(any(Gestor.class));
        verify(passwordEncoder, never()).encode(any());
    }
    
    @Test
    void deberiaMantenerElMismoIdAlActualizar() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        
        Nombre nombreOriginal = new Nombre("Juan Pérez");
        Nit nitOriginal = new Nit("123456789-0");
        Telefono telefonoOriginal = new Telefono("3001234567");
        Email emailOriginal = new Email("juan@example.com");
        Coordenadas coordenadasOriginal = new Coordenadas(
            new BigDecimal("4.60971"), 
            new BigDecimal("-74.08175")
        );
        
        Gestor existente = Gestor.builder()
                .id(id)
                .nombre(nombreOriginal)
                .nit(nitOriginal)
                .direccion("Calle 123")
                .telefono(telefonoOriginal)
                .email(emailOriginal)
                .passwordHash("hash_antiguo")
                .coordenadas(coordenadasOriginal)
                .build();
        
        Nombre nombreNuevo = new Nombre("María García");
        Nit nitNuevo = new Nit("456789123-1");
        Telefono telefonoNuevo = new Telefono("3012345678");
        Email emailNuevo = new Email("maria@example.com");
        Coordenadas coordenadasNuevas = new Coordenadas(
            new BigDecimal("4.7110"), 
            new BigDecimal("-74.0721")
        );
        String nuevaPassword = "nuevo_password";
        String hashEncodeado = "nuevo_hash_encriptado";
        
        Gestor gestorActualizado = Gestor.builder()
                .nombre(nombreNuevo)
                .nit(nitNuevo)
                .direccion("Avenida Siempre Viva 123")
                .telefono(telefonoNuevo)
                .email(emailNuevo)
                .passwordHash(nuevaPassword)
                .coordenadas(coordenadasNuevas)
                .build();
        
        Gestor gestorGuardado = Gestor.builder()
                .id(id)
                .nombre(nombreNuevo)
                .nit(nitNuevo)
                .direccion("Avenida Siempre Viva 123")
                .telefono(telefonoNuevo)
                .email(emailNuevo)
                .passwordHash(hashEncodeado)
                .coordenadas(coordenadasNuevas)
                .build();
        
        when(gestorRepository.findById(id)).thenReturn(Optional.of(existente));
        when(passwordEncoder.encode(nuevaPassword)).thenReturn(hashEncodeado);
        when(gestorRepository.save(any(Gestor.class))).thenReturn(gestorGuardado);
        
        // ACT
        Gestor resultado = actualizarGestorUseCase.ejecutar(id, gestorActualizado);
        
        // ASSERT
        assertEquals(id, resultado.getId());
        assertEquals(nombreNuevo.valor(), resultado.getNombre().valor());
        assertEquals(nitNuevo.valor(), resultado.getNit().valor());
        assertEquals("Avenida Siempre Viva 123", resultado.getDireccion());
        assertEquals(telefonoNuevo.valor(), resultado.getTelefono().valor());
        assertEquals(emailNuevo.valor(), resultado.getEmail().valor());
        assertEquals(hashEncodeado, resultado.getPasswordHash());
        assertEquals(0, coordenadasNuevas.latitud().compareTo(resultado.getCoordenadas().latitud()));
        assertEquals(0, coordenadasNuevas.longitud().compareTo(resultado.getCoordenadas().longitud()));
        
        verify(gestorRepository, times(1)).findById(id);
        verify(passwordEncoder, times(1)).encode(nuevaPassword);
        verify(gestorRepository, times(1)).save(any(Gestor.class));
    }
    
    @Test
    void deberiaEncriptarLaContrasenaAlActualizar() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        
        Nombre nombre = new Nombre("Juan Pérez");
        Nit nit = new Nit("123456789-0");
        Telefono telefono = new Telefono("3001234567");
        Email email = new Email("juan@example.com");
        Coordenadas coordenadas = new Coordenadas(
            new BigDecimal("4.60971"), 
            new BigDecimal("-74.08175")
        );
        
        Gestor existente = Gestor.builder()
                .id(id)
                .nombre(nombre)
                .nit(nit)
                .direccion("Calle 123")
                .telefono(telefono)
                .email(email)
                .passwordHash("hash_antiguo")
                .coordenadas(coordenadas)
                .build();
        
        String passwordPlana = "mi_password_segura";
        String hashEncodeado = "hash_encriptado_seguro";
        
        Gestor gestorConPassword = Gestor.builder()
                .nombre(nombre)
                .nit(nit)
                .direccion("Calle 123")
                .telefono(telefono)
                .email(email)
                .passwordHash(passwordPlana)
                .coordenadas(coordenadas)
                .build();
        
        Gestor gestorGuardado = Gestor.builder()
                .id(id)
                .nombre(nombre)
                .nit(nit)
                .direccion("Calle 123")
                .telefono(telefono)
                .email(email)
                .passwordHash(hashEncodeado)
                .coordenadas(coordenadas)
                .build();
        
        when(gestorRepository.findById(id)).thenReturn(Optional.of(existente));
        when(passwordEncoder.encode(passwordPlana)).thenReturn(hashEncodeado);
        when(gestorRepository.save(any(Gestor.class))).thenReturn(gestorGuardado);
        
        // ACT
        Gestor resultado = actualizarGestorUseCase.ejecutar(id, gestorConPassword);
        
        // ASSERT
        assertEquals(hashEncodeado, resultado.getPasswordHash());
        verify(passwordEncoder, times(1)).encode(passwordPlana);
    }
}