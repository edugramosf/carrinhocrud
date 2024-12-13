package com.lightcode.carrinho.carrinhocrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
    private String nome;

    @Column(length = 255)
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    @Column(nullable = false)
    @NotNull(message = "O preço é obrigatório.")
    @PositiveOrZero(message = "O preço deve ser zero ou positivo.")
    private BigDecimal preco;

    @Getter
    @Column(nullable = false)
    @NotNull(message = "A quantidade em estoque é obrigatória.")
    @Min(value = 0, message = "O estoque não pode ser negativo.")
    private Integer estoque = 0;

    @Enumerated(EnumType.STRING) /* Usa o nome do enum como 'ATIVO' ou 'INATIVO' para legibilidade no Banco de Dados */
    @Column(nullable = false)
    private StatusProduto status;

    /* O administrador poderá decidir se o produto está ATIVO ou INATIVO ao adicioná-lo, porém, caso não passe nenhum valor, o construtor garantirá que o status será ATIVO por padrão */
    public Produto() {
        this.status = StatusProduto.ATIVO;
    }
}

