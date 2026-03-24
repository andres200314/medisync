package com.medisync.medisync.adapters.out.persistence.jpa;

import java.util.UUID;

import com.medisync.medisync.adapters.out.persistence.entities.GestorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestorJpaRepository extends JpaRepository<GestorEntity, UUID> {
}
