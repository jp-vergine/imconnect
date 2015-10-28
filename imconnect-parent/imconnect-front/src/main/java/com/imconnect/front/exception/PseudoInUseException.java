package com.imconnect.front.exception;

public class PseudoInUseException extends Exception {

    private static final long serialVersionUID = 1L;

    public PseudoInUseException() {
	}
    
    public PseudoInUseException(final String message) {
        super(message);
    }
}
