package com.medisync.medisync.domain.valueobjects;


import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;

import java.math.BigDecimal;

public record Coordenadas(BigDecimal latitud, BigDecimal longitud) {

    public Coordenadas {
        if (latitud == null || longitud == null) {
            throw new BusinessRuleViolationException("Las coordenadas no pueden ser nulas");
        }
        if (latitud.compareTo(new BigDecimal("-90")) < 0 || latitud.compareTo(new BigDecimal("90")) > 0) {
            throw new BusinessRuleViolationException("La latitud debe estar entre -90 y 90");
        }
        if (longitud.compareTo(new BigDecimal("-180")) < 0 || longitud.compareTo(new BigDecimal("180")) > 0) {
            throw new BusinessRuleViolationException("La longitud debe estar entre -180 y 180");
        }
    }
}
