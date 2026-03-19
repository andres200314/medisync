package com.medisync.medisync.adapters.out.persistence.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.medisync.medisync.adapters.out.persistence.jpa.GestorJpaRepository;
import com.medisync.medisync.domain.entities.Gestor;
import com.medisync.medisync.domain.repositories.IGestorRepository;

@Repository
public class GestorRepositoryImpl implements IGestorRepository {

    private final GestorJpaRepository jpaRepository;

    public GestorRepositoryImpl(GestorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Gestor save(Gestor gestor) {
        return jpaRepository.save(gestor);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Gestor> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Gestor> findAll() {
        return jpaRepository.findAll();
    }
}