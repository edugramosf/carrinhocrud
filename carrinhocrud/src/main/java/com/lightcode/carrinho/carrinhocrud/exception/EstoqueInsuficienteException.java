package com.lightcode.carrinho.carrinhocrud.exception;

import lombok.Getter;

@Getter
public class EstoqueInsuficienteException extends RuntimeException {

    private final String produtoNome;
    private final int quantidadeSolicitada;
    private final int estoqueAtual;

    public EstoqueInsuficienteException(String produtoNome, int quantidadeSolicitada, int estoqueAtual) {
        super("Estoque insuficiente para o produto: " + produtoNome);
        this.produtoNome = produtoNome;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.estoqueAtual = estoqueAtual;
    }
}
