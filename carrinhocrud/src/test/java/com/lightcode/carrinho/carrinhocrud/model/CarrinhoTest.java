package com.lightcode.carrinho.carrinhocrud.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CarrinhoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarCarrinhoComDadosValidos() {
        Carrinho carrinho = new Carrinho();
        carrinho.setClienteNome("João Silva");
        carrinho.setStatus(StatusCarrinho.ATIVO);
        carrinho.setCriadoEm(LocalDateTime.now());
        carrinho.setAtualizadoEm(LocalDateTime.now());

        Set<ConstraintViolation<Carrinho>> violations = validator.validate(carrinho);

        assertTrue(violations.isEmpty(), "Carrinho válido não deve gerar violações.");
    }

    @Test
    void naoDevePermitirClienteNomeNulo() {
        Carrinho carrinho = new Carrinho();
        carrinho.setClienteNome(null);
        carrinho.setStatus(StatusCarrinho.ATIVO);

        Set<ConstraintViolation<Carrinho>> violations = validator.validate(carrinho);

        assertFalse(violations.isEmpty(), "Carrinho com clienteNome nulo deve gerar violações.");
    }

    @Test
    void naoDevePermitirStatusNulo() {
        Carrinho carrinho = new Carrinho();
        carrinho.setClienteNome("Maria Oliveira");
        carrinho.setStatus(null);

        Set<ConstraintViolation<Carrinho>> violations = validator.validate(carrinho);

        assertFalse(violations.isEmpty(), "Carrinho com status nulo deve gerar violações.");
    }
}
