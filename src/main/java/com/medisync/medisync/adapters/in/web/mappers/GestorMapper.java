package com.medisync.medisync.adapters.in.web.mappers;

import com.medisync.medisync.domain.valueobjects.*;
import org.springframework.stereotype.Component;

import com.medisync.medisync.adapters.in.web.dto.gestor.GestorRequestDTO;
import com.medisync.medisync.adapters.in.web.dto.gestor.GestorResponseDTO;
import com.medisync.medisync.domain.models.Gestor;


@Component
public class GestorMapper {

    public Gestor toDomain(GestorRequestDTO dto) {
        return Gestor.builder()
                .nombre(new Nombre(dto.getNombre()))
                .nit(new Nit(dto.getNit()))
                .direccion(dto.getDireccion())
                .telefono(new Telefono(dto.getTelefono()))
                .email(new Email(dto.getEmail()))
                .passwordHash(dto.getPassword())
                .coordenadas(new Coordenadas(dto.getLatitud(), dto.getLongitud()))
                .build();
    }

    public GestorResponseDTO toResponse(Gestor gestor) {
        return GestorResponseDTO.builder()
                .id(gestor.getId())
                .nombre(gestor.getNombre().valor())
                .nit(gestor.getNit().valor())
                .direccion(gestor.getDireccion())
                .telefono(gestor.getTelefono().valor())
                .email(gestor.getEmail().valor())
                .latitud(gestor.getCoordenadas().latitud())
                .longitud(gestor.getCoordenadas().longitud())
                .build();
    }
}