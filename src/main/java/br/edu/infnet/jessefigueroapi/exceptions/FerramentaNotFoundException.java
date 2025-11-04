package br.edu.infnet.jessefigueroapi.exceptions;

public class FerramentaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FerramentaNotFoundException(String message) {
        super(message);
    }
}
