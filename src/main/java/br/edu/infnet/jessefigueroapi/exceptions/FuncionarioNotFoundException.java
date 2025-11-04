package br.edu.infnet.jessefigueroapi.exceptions;

public class FuncionarioNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FuncionarioNotFoundException(String message) {
        super(message);
    }
}

