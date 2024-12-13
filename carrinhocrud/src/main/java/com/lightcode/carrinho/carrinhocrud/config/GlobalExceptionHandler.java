package com.lightcode.carrinho.carrinhocrud.config;

import com.lightcode.carrinho.carrinhocrud.exception.EstoqueInsuficienteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    private static final int CONFLICT = HttpStatus.CONFLICT.value();
    private static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("Erro de validação capturado: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return buildResponse("Erro de validação", errors, BAD_REQUEST);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> handleEstoqueInsuficiente(EstoqueInsuficienteException ex) {
        logger.error("Estoque insuficiente: Produto [{}], Quantidade Solicitada [{}], Estoque Atual [{}]",
                ex.getProdutoNome(), ex.getQuantidadeSolicitada(), ex.getEstoqueAtual());

        Map<String, Object> details = new HashMap<>();
        details.put("produto", ex.getProdutoNome());
        details.put("quantidadeSolicitada", ex.getQuantidadeSolicitada());
        details.put("estoqueAtual", ex.getEstoqueAtual());

        return buildResponse("Estoque insuficiente", details, CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        logger.error("Erro interno no servidor: {}", ex.getMessage(), ex);

        return buildResponse("Erro interno no servidor", null, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String errorMessage, Map<String, ?> details, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status);
        response.put("error", errorMessage);
        if (details != null) {
            response.put("details", details);
        }
        return ResponseEntity.status(status).body(response);
    }
}