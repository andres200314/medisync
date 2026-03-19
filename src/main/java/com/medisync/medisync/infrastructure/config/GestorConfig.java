package com.medisync.medisync.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.medisync.medisync.application.usecases.gestor.CrearGestorUseCase;
import com.medisync.medisync.domain.repositories.IGestorRepository;

@Configuration
public class GestorConfig {

    @Bean
    public CrearGestorUseCase crearGestorUseCase(IGestorRepository gestorRepository,
                                                  BCryptPasswordEncoder passwordEncoder) {
        return new CrearGestorUseCase(gestorRepository, passwordEncoder);
    }
}