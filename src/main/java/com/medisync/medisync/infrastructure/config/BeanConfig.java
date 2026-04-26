package com.medisync.medisync.infrastructure.config;

import com.medisync.medisync.application.usecases.gestor.*;
import com.medisync.medisync.application.usecases.inventario.*;
import com.medisync.medisync.application.usecases.medicamento.*;
import com.medisync.medisync.domain.repositories.*;
import com.medisync.medisync.domain.services.IPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // Medicamento
    @Bean
    public CrearMedicamentoUseCase crearMedicamentoUseCase(IMedicamentoRepository medicamentoRepository) {
        return new CrearMedicamentoUseCase(medicamentoRepository);
    }

    @Bean
    public EliminarMedicamentoUseCase eliminarMedicamentoUseCase(IMedicamentoRepository medicamentoRepository) {
        return new EliminarMedicamentoUseCase(medicamentoRepository);
    }

    @Bean
    public ObtenerMedicamentosUseCase obtenerMedicamentosUseCase(IMedicamentoRepository medicamentoRepository) {
        return new ObtenerMedicamentosUseCase(medicamentoRepository);
    }

    @Bean
    public ObtenerMedicamentoPorIdUseCase obtenerMedicamentoPorIdUseCase(IMedicamentoRepository medicamentoRepository) {
        return new ObtenerMedicamentoPorIdUseCase(medicamentoRepository);
    }

    @Bean
    public ActualizarMedicamentoUseCase actualizarMedicamentoUseCase(IMedicamentoRepository medicamentoRepository) {
        return new ActualizarMedicamentoUseCase(medicamentoRepository);
    }

    // Inventario
    @Bean
    public CrearInventarioUseCase crearInventarioUseCase(IInventarioRepository inventarioRepository) {
        return new CrearInventarioUseCase(inventarioRepository);
    }

    @Bean
    public ObtenerInventariosUseCase obtenerInventariosUseCase(IInventarioRepository inventarioRepository) {
        return new ObtenerInventariosUseCase(inventarioRepository);
    }

    @Bean
    public ObtenerInventarioPorGestorUseCase obtenerInventarioPorGestorUseCase(IInventarioRepository inventarioRepository) {
        return new ObtenerInventarioPorGestorUseCase(inventarioRepository);
    }

    // Gestor
    @Bean
    public CrearGestorUseCase crearGestorUseCase(IGestorRepository gestorRepository,
                                                 IPasswordEncoder passwordEncoder,
                                                 CrearInventarioUseCase crearInventarioUseCase) {
        return new CrearGestorUseCase(gestorRepository, passwordEncoder, crearInventarioUseCase);
    }

    @Bean
    public ObtenerGestoresUseCase obtenerGestoresUseCase(IGestorRepository gestorRepository) {
        return new ObtenerGestoresUseCase(gestorRepository);
    }

    @Bean
    public ObtenerGestorPorIdUseCase obtenerGestorPorIdUseCase(IGestorRepository gestorRepository) {
        return new ObtenerGestorPorIdUseCase(gestorRepository);
    }

    @Bean
    public ActualizarGestorUseCase actualizarGestorUseCase(IGestorRepository gestorRepository) {
        return new ActualizarGestorUseCase(gestorRepository);
    }

    @Bean
    public CambiarPasswordGestorUseCase cambiarPasswordGestorUseCase(
            IGestorRepository gestorRepository,
            IPasswordEncoder passwordEncoder) {
        return new CambiarPasswordGestorUseCase(gestorRepository, passwordEncoder);
    }

    @Bean
    public EliminarGestorUseCase eliminarGestorUseCase(
            IGestorRepository gestorRepository,
            IInventarioRepository inventarioRepository) {
        return new EliminarGestorUseCase(gestorRepository, inventarioRepository);
    }
}
