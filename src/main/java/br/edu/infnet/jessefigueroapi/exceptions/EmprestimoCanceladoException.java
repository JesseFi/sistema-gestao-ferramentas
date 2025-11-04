package br.edu.infnet.jessefigueroapi.exceptions;

public class EmprestimoCanceladoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmprestimoCanceladoException(String message) {
        super(message);
    }
}
