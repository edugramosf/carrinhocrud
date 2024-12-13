package com.lightcode.carrinho.carrinhocrud.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void deveValidarProdutoComDadosValidos() {
        Produto produto = new Produto();
        produto.setNome("Notebook Dell");
        produto.setDescricao("Notebook com 16GB de RAM e 512GB SSD");
        produto.setPreco(new BigDecimal("4500.00"));
        produto.setEstoque(10); // Certifique-se de definir um valor válido para o estoque

        Set<ConstraintViolation<Produto>> violacoes = validator.validate(produto);
        assertTrue(violacoes.isEmpty(), "Produto válido não deve gerar violações.");
    }

    @Test
    void naoDevePermitirNomeNuloOuEmBranco() {
        Produto produto = new Produto();
        produto.setNome("");
        produto.setDescricao("Descrição válida");
        produto.setPreco(BigDecimal.valueOf(49.99));

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);

        assertFalse(violations.isEmpty(), "Produto com nome vazio deve gerar violações.");
    }

    @Test
    void naoDevePermitirPrecoNegativo() {
        Produto produto = new Produto();
        produto.setNome("Produto Inválido");
        produto.setDescricao("Descrição válida");
        produto.setPreco(BigDecimal.valueOf(-10.00));

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);

        assertFalse(violations.isEmpty(), "Produto com preço negativo deve gerar violações.");
    }

    @Test
    void naoDevePermitirDescricaoMuitoLonga() {
        Produto produto = new Produto();
        produto.setNome("Produto Inválido");
        produto.setDescricao("a".repeat(256));
        produto.setPreco(BigDecimal.valueOf(49.99));

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);

        assertFalse(violations.isEmpty(), "Produto com descrição maior que 255 caracteres deve gerar violações.");
    }
}
