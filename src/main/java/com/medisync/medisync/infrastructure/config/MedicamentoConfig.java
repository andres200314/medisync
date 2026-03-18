package com.medisync.medisync.infrastructure.config;


import com.medisync.medisync.application.usecases.medicamento.*;
import com.medisync.medisync.domain.repositories.IMedicamentoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MedicamentoConfig {

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


}
