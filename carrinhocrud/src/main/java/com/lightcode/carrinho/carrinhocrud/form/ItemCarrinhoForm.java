package com.lightcode.carrinho.carrinhocrud.form;

import com.lightcode.carrinho.carrinhocrud.model.ItemCarrinho;
import com.lightcode.carrinho.carrinhocrud.model.Produto;
import com.lightcode.carrinho.carrinhocrud.model.StatusItem;
import com.lightcode.carrinho.carrinhocrud.validator.ProdutoExistente;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCarrinhoForm {

    @ProdutoExistente
    private Long produtoId;

    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    private Integer quantidade;

    private String status; /* Não obrigatório, porém o padrão será "ATIVO" */

    public ItemCarrinho toModel(Produto produto) {
        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setProduto(produto);
        itemCarrinho.setQuantidade(this.quantidade);

        /* Se o status não for definido, será "ATIVO" por padrão */
        if (this.status == null || this.status.isEmpty()) {
            itemCarrinho.setStatus(StatusItem.ATIVO);
        } else {
            itemCarrinho.setStatus(StatusItem.valueOf(this.status)); /* Aqui, Usa o status fornecido pelo usuário */
        }

        return itemCarrinho;
    }
}
