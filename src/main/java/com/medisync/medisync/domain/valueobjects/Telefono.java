package com.medisync.medisync.domain.valueobjects;


import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;

public record Telefono(String valor) {

    public Telefono {
        if (valor == null || valor.isBlank()) {
            throw new BusinessRuleViolationException("El teléfono no puede ser nulo o vacío");
        }
        if (!valor.matches("^[0-9]{10}$")) {
            throw new BusinessRuleViolationException("El teléfono debe tener 10 dígitos: " + valor);
        }
    }
}
