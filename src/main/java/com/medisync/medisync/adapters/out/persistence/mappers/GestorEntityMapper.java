package com.medisync.medisync.adapters.out.persistence.mappers;

import org.springframework.stereotype.Component;

import com.medisync.medisync.adapters.out.persistence.entities.GestorEntity;
import com.medisync.medisync.domain.models.Gestor;


@Component
public class GestorEntityMapper {

    public Gestor toDomain(GestorEntity entity) {
        return Gestor.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .nit(entity.getNit())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .latitud(entity.getLatitud())
                .longitud(entity.getLongitud())
                .build();
    }

    public GestorEntity toEntity(Gestor gestor) {
        return GestorEntity.builder()
                .id(gestor.getId())
                .nombre(gestor.getNombre())
                .nit(gestor.getNit())
                .direccion(gestor.getDireccion())
                .telefono(gestor.getTelefono())
                .email(gestor.getEmail())
                .passwordHash(gestor.getPasswordHash())
                .latitud(gestor.getLatitud())
                .longitud(gestor.getLongitud())
                .build();
    }
}