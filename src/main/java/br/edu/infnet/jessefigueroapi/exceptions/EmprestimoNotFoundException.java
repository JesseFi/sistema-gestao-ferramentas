package br.edu.infnet.jessefigueroapi.exceptions;

public class EmprestimoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmprestimoNotFoundException(String message) {
        super(message);
    }
}
