package com.lightcode.carrinho.carrinhocrud.form;

import com.lightcode.carrinho.carrinhocrud.model.Produto;
import com.lightcode.carrinho.carrinhocrud.model.StatusProduto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class ProdutoForm {
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser maior que zero.")
    private BigDecimal preco;

    @NotNull(message = "O estoque é obrigatório.")
    @Min(value = 0, message = "O estoque não pode ser negativo.")
    private Integer estoque;

    private StatusProduto status;

    public Produto toModel() {
        Produto produto = new Produto();
        produto.setNome(this.nome);
        produto.setDescricao(this.descricao);
        produto.setPreco(this.preco);
        produto.setEstoque(this.estoque);
        produto.setStatus(this.status != null ? this.status : StatusProduto.ATIVO);
        return produto;
    }
}
