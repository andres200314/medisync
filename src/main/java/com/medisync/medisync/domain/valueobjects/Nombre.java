package com.medisync.medisync.domain.valueobjects;

public record Nombre(String valor) {

    public Nombre {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (valor.trim().length() < 4) {
            throw new IllegalArgumentException("El nombre debe tener al menos 4 caracteres");
        }
        valor = valor.trim();
    }

    public static Nombre of(String valor) {
        return new Nombre(valor);
    }
}