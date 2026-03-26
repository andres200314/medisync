package com.medisync.medisync.application.usecases.gestor;

import java.util.UUID;

import com.medisync.medisync.adapters.in.web.exceptions.GestorNotFoundException;
import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.repositories.IGestorRepository;
import com.medisync.medisync.domain.services.IPasswordEncoder;

public class ActualizarGestorUseCase {

    private final IGestorRepository gestorRepository;
    private final IPasswordEncoder passwordEncoder;

    public ActualizarGestorUseCase(IGestorRepository gestorRepository,
                                    IPasswordEncoder passwordEncoder) {
        this.gestorRepository = gestorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Gestor ejecutar(UUID id, Gestor gestor) {
        
        gestorRepository.findById(id)
                .orElseThrow(() -> new GestorNotFoundException(id.toString()));

      
        Gestor actualizado = Gestor.builder()
                .id(id)
                .nombre(gestor.getNombre())  
                .nit(gestor.getNit())        
                .direccion(gestor.getDireccion())
                .telefono(gestor.getTelefono())  
                .email(gestor.getEmail())       
                .passwordHash(passwordEncoder.encode(gestor.getPasswordHash()))
                .coordenadas(gestor.getCoordenadas())  
                .build();

        return gestorRepository.save(actualizado);
    }
}