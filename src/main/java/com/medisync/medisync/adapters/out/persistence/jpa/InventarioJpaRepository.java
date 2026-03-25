package com.medisync.medisync.adapters.out.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisync.medisync.adapters.out.persistence.entities.InventarioEntity;

public interface InventarioJpaRepository extends JpaRepository<InventarioEntity, UUID> {
    List<InventarioEntity> findByGestorId(UUID gestorId);
    List<InventarioEntity> findByMedicamentoIdAndGestorId(UUID medicamentoId, UUID gestorId);
}