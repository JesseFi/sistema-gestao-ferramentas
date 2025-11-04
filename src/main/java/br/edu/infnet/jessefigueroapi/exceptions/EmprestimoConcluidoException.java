package br.edu.infnet.jessefigueroapi.exceptions;

public class EmprestimoConcluidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmprestimoConcluidoException(String message) {
        super(message);
    }
}
