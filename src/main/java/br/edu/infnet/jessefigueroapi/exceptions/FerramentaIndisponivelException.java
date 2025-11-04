package br.edu.infnet.jessefigueroapi.exceptions;

public class FerramentaIndisponivelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FerramentaIndisponivelException(String message) {
        super(message);
    }
}
