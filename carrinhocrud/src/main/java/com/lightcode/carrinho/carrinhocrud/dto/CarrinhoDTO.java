package com.lightcode.carrinho.carrinhocrud.dto;

import com.lightcode.carrinho.carrinhocrud.model.Carrinho;
import com.lightcode.carrinho.carrinhocrud.model.StatusCarrinho;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CarrinhoDTO {
    @Schema(description = "ID do carrinho", example = "1")
    private Long id;

    @Schema(description = "Nome do cliente associado ao carrinho", example = "João Silva")
    private String clienteNome;

    @Schema(description = "Status do carrinho", example = "ATIVO")
    private StatusCarrinho status;

    @Schema(description = "Data de criação do carrinho", example = "2024-12-06T10:15:30")
    private LocalDateTime criadoEm;

    @Schema(description = "Data de atualização do carrinho", example = "2024-12-07T11:20:45")
    private LocalDateTime atualizadoEm;

    @Schema(description = "Lista de itens do carrinho")
    private List<ItemCarrinhoDTO> itens;

    public CarrinhoDTO(Carrinho carrinho) {
        this.id = carrinho.getId();
        this.clienteNome = carrinho.getClienteNome();
        this.status = carrinho.getStatus();
        this.criadoEm = carrinho.getCriadoEm();
        this.atualizadoEm = carrinho.getAtualizadoEm();
        this.itens = carrinho.getItens() != null
                ? carrinho.getItens()
                .stream()
                .map(ItemCarrinhoDTO::new)
                .collect(Collectors.toList())
                : List.of(); /* Lista vazia como fallback */
    }
}
