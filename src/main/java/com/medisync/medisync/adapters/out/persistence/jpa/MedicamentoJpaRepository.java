package com.medisync.medisync.adapters.out.persistence.jpa;

import com.medisync.medisync.domain.entities.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicamentoJpaRepository extends JpaRepository<Medicamento, UUID> {
}
