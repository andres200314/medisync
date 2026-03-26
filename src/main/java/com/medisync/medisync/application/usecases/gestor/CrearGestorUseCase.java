package com.medisync.medisync.application.usecases.gestor;
import com.medisync.medisync.domain.models.Gestor;
import com.medisync.medisync.domain.repositories.IGestorRepository;
import com.medisync.medisync.domain.services.IPasswordEncoder;

public class CrearGestorUseCase {

    private final IGestorRepository gestorRepository;
    private final IPasswordEncoder passwordEncoder;

    public CrearGestorUseCase(IGestorRepository gestorRepository, IPasswordEncoder passwordEncoder) {
        this.gestorRepository = gestorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Gestor ejecutar(Gestor gestor) {
        gestor.setPasswordHash(passwordEncoder.encode(gestor.getPasswordHash()));
        return gestorRepository.save(gestor);
    }
}