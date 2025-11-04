package br.edu.infnet.jessefigueroapi.exceptions;

public class AlteracaoNaoPermitidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlteracaoNaoPermitidaException(String message) {
        super(message);
    }
}
