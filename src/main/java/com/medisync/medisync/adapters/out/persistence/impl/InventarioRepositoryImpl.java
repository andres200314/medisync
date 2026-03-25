package com.medisync.medisync.adapters.out.persistence.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.medisync.medisync.adapters.out.persistence.entities.GestorEntity;
import com.medisync.medisync.adapters.out.persistence.entities.InventarioEntity;
import com.medisync.medisync.adapters.out.persistence.entities.MedicamentoEntity;
import com.medisync.medisync.adapters.out.persistence.jpa.InventarioJpaRepository;
import com.medisync.medisync.adapters.out.persistence.mappers.InventarioEntityMapper;
import com.medisync.medisync.domain.models.Inventario;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class InventarioRepositoryImpl implements IInventarioRepository {

    private final InventarioJpaRepository jpaRepository;
    private final InventarioEntityMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public InventarioRepositoryImpl(InventarioJpaRepository jpaRepository,
                                     InventarioEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Inventario save(Inventario inventario) {
        InventarioEntity entity = InventarioEntity.builder()
                .id(inventario.getId())
                .medicamento(entityManager.getReference(MedicamentoEntity.class, inventario.getMedicamento().getId()))
                .gestor(entityManager.getReference(GestorEntity.class, inventario.getGestor().getId()))
                .cantidad(inventario.getCantidad())
                .precioUnitario(inventario.getPrecioUnitario())
                .build();

        InventarioEntity guardado = jpaRepository.save(entity);
        InventarioEntity completo = jpaRepository.findById(guardado.getId())
                .orElseThrow(() -> new RuntimeException("Error al recuperar inventario guardado"));
        return mapper.toDomain(completo);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Inventario> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Inventario> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Inventario> findByGestorId(UUID gestorId) {
        return jpaRepository.findByGestorId(gestorId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Inventario> findByMedicamentoIdAndGestorId(UUID medicamentoId, UUID gestorId) {
        return jpaRepository.findByMedicamentoIdAndGestorId(medicamentoId, gestorId)
            .stream().map(mapper::toDomain).toList();
}
}