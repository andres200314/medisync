package com.medisync.medisync.domain.valueobjects;


import java.math.BigDecimal;

public record Coordenadas(BigDecimal latitud, BigDecimal longitud) {

    public Coordenadas {
        if (latitud == null || longitud == null) {
            throw new IllegalArgumentException("Las coordenadas no pueden ser nulas");
        }
        if (latitud.compareTo(new BigDecimal("-90")) < 0 || latitud.compareTo(new BigDecimal("90")) > 0) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }
        if (longitud.compareTo(new BigDecimal("-180")) < 0 || longitud.compareTo(new BigDecimal("180")) > 0) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
        }
    }
}
