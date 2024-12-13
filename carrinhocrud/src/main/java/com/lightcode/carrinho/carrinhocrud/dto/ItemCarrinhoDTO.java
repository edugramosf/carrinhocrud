package com.lightcode.carrinho.carrinhocrud.dto;

import com.lightcode.carrinho.carrinhocrud.model.ItemCarrinho;
import com.lightcode.carrinho.carrinhocrud.model.StatusItem;
import lombok.Getter;

@Getter
public class ItemCarrinhoDTO {
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private StatusItem status;

    public ItemCarrinhoDTO(ItemCarrinho item) {
        this.id = item.getId();
        this.produtoId = item.getProduto().getId();
        this.quantidade = item.getQuantidade();
        this.status = item.getStatus();
    }

}
