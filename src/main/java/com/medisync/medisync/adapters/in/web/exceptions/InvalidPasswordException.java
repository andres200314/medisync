package com.medisync.medisync.adapters.in.web.exceptions;

import com.medisync.medisync.domain.exceptions.BusinessRuleViolationException;

public class InvalidPasswordException extends BusinessRuleViolationException {
    
    public InvalidPasswordException(String message) {
        super(message);
    }
    
    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}