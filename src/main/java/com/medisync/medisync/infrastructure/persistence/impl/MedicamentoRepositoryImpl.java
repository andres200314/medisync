package com.medisync.medisync.infrastructure.persistence.impl;

import com.medisync.medisync.domain.entities.Medicamento;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;
import com.medisync.medisync.infrastructure.persistence.jpa.MedicamentoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MedicamentoRepositoryImpl implements IMedicamentoRepository {

    private final MedicamentoJpaRepository jpaRepository;

    public MedicamentoRepositoryImpl(MedicamentoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Medicamento save(Medicamento medicamento) {
        return jpaRepository.save(medicamento);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Medicamento> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Medicamento> findAll() {
        return jpaRepository.findAll();
    }
}
