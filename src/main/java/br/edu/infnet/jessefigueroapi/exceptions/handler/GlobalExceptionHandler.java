package br.edu.infnet.jessefigueroapi.exceptions.handler;

import br.edu.infnet.jessefigueroapi.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FerramentaNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleFerramentaNotFoundException(FerramentaNotFoundException e) {

        Map<String, String> map = new HashMap<>();

        map.put("timestamp", LocalDateTime.now().toString());
        map.put("status", HttpStatus.NOT_FOUND.toString());
        map.put("message", e.getMessage());
        map.put("detail", "A ferramenta solicitada não está disponível para empréstimo");

        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FerramentaIndisponivelException.class)
    public ResponseEntity<Map<String, String>> handleFerramentaIndisponivelException(FerramentaIndisponivelException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.CONFLICT.toString());
        error.put("message", e.getMessage());
        error.put("detail", "A ferramenta solicitada não está disponível para empréstimo");

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FuncionarioNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleFuncionarioNotFoundException(FuncionarioNotFoundException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.NOT_FOUND.toString());
        error.put("message", e.getMessage());
        error.put("detail", "Funcionário não encontrado");

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FuncionarioInativoException.class)
    public ResponseEntity<Map<String, String>> handleFuncionarioInativoException(FuncionarioInativoException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", String.valueOf(HttpStatus.CONFLICT.value()));
        error.put("message", e.getMessage());
        error.put("detail", "O funcionário está inativo e não pode realizar empréstimos");

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmprestimoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmprestimoNotFoundException(EmprestimoNotFoundException e) {

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now().toString());
        errorDetails.put("status", HttpStatus.NOT_FOUND.toString());
        errorDetails.put("message", e.getMessage());
        errorDetails.put("detail", "Emprestimo não encontrado");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmprestimoConcluidoException.class)
    public ResponseEntity<Map<String, String>> handleEmprestimoConcluidoException(EmprestimoConcluidoException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.CONFLICT.toString());
        error.put("message", e.getMessage());
        error.put("detail", "Não é possível alterar um empréstimo já concluído");

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmprestimoCanceladoException.class)
    public ResponseEntity<Map<String, String>> handleEmprestimoCanceladoException(EmprestimoCanceladoException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.CONFLICT.toString());
        error.put("message", e.getMessage());
        error.put("detail", "Não é possível alterar um empréstimo cancelado");

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlteracaoNaoPermitidaException.class)
    public ResponseEntity<Map<String, String>> handleAlteracaoNaoPermitidaException(AlteracaoNaoPermitidaException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.BAD_REQUEST.toString());
        error.put("message", e.getMessage());
        error.put("detail", "A operação solicitada não é permitida neste contexto");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<Map<String, String>> handleDataInvalidaException(DataInvalidaException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.BAD_REQUEST.toString());
        error.put("message", e.getMessage());
        error.put("detail", "A data fornecida não é válida para esta operação");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.BAD_REQUEST.toString());
        error.put("message", e.getMessage());
        error.put("detail", "Um argumento inválido foi fornecido para essa operação");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.CONFLICT.toString());
        error.put("message", e.getMessage());
        error.put("detail", "A operação não pode ser executada no estado atual");

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.CONFLICT.toString());
        error.put("message", e.getMessage());
        error.put("detail", "Possível violação de chave única ou restrição de integridade");

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {

        Map<String, String> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.put("error", e.getMessage());
        error.put("detail", "RuntimeException. error");

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}