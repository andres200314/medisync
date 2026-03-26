package com.medisync.medisync.adapters.in.web.exceptions;

public class GestorNotFoundException extends RuntimeException {
    public GestorNotFoundException(String id) {
        super("Gestor no encontrado con id: " + id);
    }
}
