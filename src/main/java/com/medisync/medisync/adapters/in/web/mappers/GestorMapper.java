package com.medisync.medisync.adapters.in.web.mappers;

import org.springframework.stereotype.Component;

import com.medisync.medisync.adapters.in.web.dto.gestor.GestorRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.gestor.GestorResponseDTO;
import com.medisync.medisync.domain.models.Gestor;


@Component
public class GestorMapper {

    public Gestor toEntity(GestorRequestDTO dto) {
        return Gestor.builder()
                .nombre(dto.getNombre())
                .nit(dto.getNit())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .passwordHash(dto.getPassword())
                .latitud(dto.getLatitud())
                .longitud(dto.getLongitud())
                .build();
    }

    public GestorResponseDTO toResponse(Gestor gestor) {
        return GestorResponseDTO.builder()
                .id(gestor.getId())
                .nombre(gestor.getNombre())
                .nit(gestor.getNit())
                .direccion(gestor.getDireccion())
                .telefono(gestor.getTelefono())
                .email(gestor.getEmail())
                .latitud(gestor.getLatitud())
                .longitud(gestor.getLongitud())
                .build();
    }
}