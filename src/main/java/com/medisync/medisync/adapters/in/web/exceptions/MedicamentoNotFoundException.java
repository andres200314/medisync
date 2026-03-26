package com.medisync.medisync.adapters.in.web.exceptions;

public class MedicamentoNotFoundException extends RuntimeException {
    public MedicamentoNotFoundException(String id) {
        super("Medicamento no encontrado con id: " + id);
    }
}