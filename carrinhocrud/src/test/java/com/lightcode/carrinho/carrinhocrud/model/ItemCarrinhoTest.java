package com.lightcode.carrinho.carrinhocrud.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemCarrinhoTest {

    @Test
    void deveCriarItemCarrinhoComDadosValidos() {
        Carrinho carrinho = new Carrinho();
        Produto produto = new Produto();

        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setCarrinho(carrinho);
        itemCarrinho.setProduto(produto);
        itemCarrinho.setQuantidade(5);
        itemCarrinho.setStatus(StatusItem.ATIVO);

        assertNotNull(itemCarrinho.getCarrinho(), "O carrinho não deve ser nulo.");
        assertNotNull(itemCarrinho.getProduto(), "O produto não deve ser nulo.");
        assertEquals(5, itemCarrinho.getQuantidade(), "A quantidade deve ser 5.");
        assertEquals(StatusItem.ATIVO, itemCarrinho.getStatus(), "O status deve ser ATIVO.");
    }

    @Test
    void deveGerenciarCamposDeAuditoria() {
        ItemCarrinho itemCarrinho = new ItemCarrinho();
        LocalDateTime antesDaPersistencia = LocalDateTime.now();

        /* Simula o comportamento do JPA para @PrePersist */
        itemCarrinho.onCreate();

        assertNotNull(itemCarrinho.getCriadoEm(), "O campo criadoEm não deve ser nulo após persistência.");
        assertNotNull(itemCarrinho.getAtualizadoEm(), "O campo atualizadoEm não deve ser nulo após persistência.");
        assertTrue(itemCarrinho.getCriadoEm().isAfter(antesDaPersistencia) ||
                        itemCarrinho.getCriadoEm().isEqual(antesDaPersistencia),
                "O campo criadoEm deve ser maior ou igual ao momento antes da persistência.");
        assertEquals(itemCarrinho.getCriadoEm(), itemCarrinho.getAtualizadoEm(),
                "Os campos criadoEm e atualizadoEm devem ser iguais após a criação.");

        /* Simula o comportamento do JPA para @PreUpdate */
        itemCarrinho.onUpdate();

        assertTrue(itemCarrinho.getAtualizadoEm().isEqual(itemCarrinho.getCriadoEm()) ||
                        itemCarrinho.getAtualizadoEm().isAfter(itemCarrinho.getCriadoEm()),
                "O campo atualizadoEm deveria ser maior ou igual ao criadoEm após a atualização, mas não é.");

    }
}
