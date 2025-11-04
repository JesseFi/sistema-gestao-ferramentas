package br.edu.infnet.jessefigueroapi.exceptions;

public class FuncionarioInativoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FuncionarioInativoException(String message) {
        super(message);
    }
}
