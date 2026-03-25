package com.medisync.medisync.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.medisync.medisync.application.usecases.inventario.CrearInventarioUseCase;
import com.medisync.medisync.application.usecases.inventario.ObtenerInventariosUseCase;
import com.medisync.medisync.domain.repositories.IInventarioRepository;

@Configuration
public class InventarioConfig {

    @Bean
    public CrearInventarioUseCase crearInventarioUseCase(IInventarioRepository inventarioRepository) {
        return new CrearInventarioUseCase(inventarioRepository);
    }

    @Bean
    public ObtenerInventariosUseCase obtenerInventariosUseCase(IInventarioRepository inventarioRepository) {
        return new ObtenerInventariosUseCase(inventarioRepository);
    }
}