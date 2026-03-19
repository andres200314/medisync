package com.medisync.medisync.adapters.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisync.medisync.domain.entities.Gestor;

public interface GestorJpaRepository extends JpaRepository<Gestor, UUID> {
}
