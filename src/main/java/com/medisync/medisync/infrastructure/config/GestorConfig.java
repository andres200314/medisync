package com.medisync.medisync.infrastructure.config;

import com.medisync.medisync.application.usecases.gestor.ObtenerGestorPorIdUseCase;
import com.medisync.medisync.domain.services.IPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.medisync.medisync.application.usecases.gestor.CrearGestorUseCase;
import com.medisync.medisync.application.usecases.gestor.ObtenerGestoresUseCase;
import com.medisync.medisync.domain.repositories.IGestorRepository;

@Configuration
public class GestorConfig {

    @Bean
    public CrearGestorUseCase crearGestorUseCase(IGestorRepository gestorRepository,
                                                  IPasswordEncoder passwordEncoder) {
        return new CrearGestorUseCase(gestorRepository, passwordEncoder);
    }

    @Bean
    public ObtenerGestoresUseCase obtenerGestoresUseCase(IGestorRepository gestorRepository) {
        return new ObtenerGestoresUseCase(gestorRepository);
    }

    @Bean
    public ObtenerGestorPorIdUseCase obtenerGestorPorIdUseCase(IGestorRepository gestorRepository) {
        return new ObtenerGestorPorIdUseCase(gestorRepository);
    }
}