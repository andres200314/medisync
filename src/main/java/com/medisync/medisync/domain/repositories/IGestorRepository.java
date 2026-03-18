package com.medisync.medisync.domain.repositories;

import com.medisync.medisync.domain.entities.Gestor;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IGestorRepository {
    Gestor save(Gestor gestor);
    void deleteById(UUID id);
    Optional<Gestor> findById(UUID id);
    List<Gestor> findAll();
}
