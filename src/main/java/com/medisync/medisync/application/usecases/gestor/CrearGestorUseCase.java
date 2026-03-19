package com.medisync.medisync.application.usecases.gestor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.medisync.medisync.domain.entities.Gestor;
import com.medisync.medisync.domain.repositories.IGestorRepository;

public class CrearGestorUseCase {

    private final IGestorRepository gestorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CrearGestorUseCase(IGestorRepository gestorRepository, BCryptPasswordEncoder passwordEncoder) {
        this.gestorRepository = gestorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Gestor ejecutar(Gestor gestor) {
        gestor.setPasswordHash(passwordEncoder.encode(gestor.getPasswordHash()));
        return gestorRepository.save(gestor);
    }
}