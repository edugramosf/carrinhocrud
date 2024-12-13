package com.lightcode.carrinho.carrinhocrud.dto;

import com.lightcode.carrinho.carrinhocrud.model.Produto;
import com.lightcode.carrinho.carrinhocrud.model.StatusProduto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private StatusProduto status;

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.estoque = produto.getEstoque();
        this.status = produto.getStatus();
    }
}
