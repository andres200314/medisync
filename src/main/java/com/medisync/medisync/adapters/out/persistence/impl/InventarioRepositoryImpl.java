package com.medisync.medisync.adapters.out.persistence.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medisync.medisync.adapters.out.persistence.entities.InventarioEntity;
import com.medisync.medisync.adapters.out.persistence.jpa.InventarioJpaRepository;
import com.medisync.medisync.adapters.out.persistence.mappers.InventarioEntityMapper;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

@Repository
public class InventarioRepositoryImpl implements IInventarioRepository {

    private final InventarioJpaRepository jpaRepository;
    private final InventarioEntityMapper mapper;

    public InventarioRepositoryImpl(InventarioJpaRepository jpaRepository,
                                    InventarioEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Inventario save(Inventario inventario) {
        InventarioEntity entity = mapper.toEntity(inventario);

        InventarioEntity guardado = jpaRepository.save(entity);

        return mapper.toDomain(guardado);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!jpaRepository.existsById(id)) {
            throw new RuntimeException("Inventario no encontrado con ID: " + id);
        }
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Inventario> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Inventario> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Inventario> findByGestorId(UUID gestorId) {
        return jpaRepository.findByGestorId(gestorId)
                .map(mapper::toDomain);
    }
}