package com.lightcode.carrinho.carrinhocrud.controller;

import com.lightcode.carrinho.carrinhocrud.dto.ItemCarrinhoDTO;
import com.lightcode.carrinho.carrinhocrud.exception.EstoqueInsuficienteException;
import com.lightcode.carrinho.carrinhocrud.form.ItemCarrinhoForm;
import com.lightcode.carrinho.carrinhocrud.model.ItemCarrinho;
import com.lightcode.carrinho.carrinhocrud.model.Produto;
import com.lightcode.carrinho.carrinhocrud.service.ItemCarrinhoService;
import com.lightcode.carrinho.carrinhocrud.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrinhos/{carrinhoId}/itens")
public class ItemCarrinhoController {

    private final ItemCarrinhoService service;
    private final ProdutoService produtoService;

    public ItemCarrinhoController(ItemCarrinhoService service, ProdutoService produtoService) {
        this.service = service;
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Adicionar item ao carrinho", description = "Adiciona um novo item a um carrinho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item adicionado ao carrinho."),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados."),
            @ApiResponse(responseCode = "409", description = "Estoque insuficiente.")})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto contendo os dados do item a ser adicionado ao carrinho.",
            required = true
    )
    public ResponseEntity<ItemCarrinhoDTO> adicionar(@PathVariable Long carrinhoId, @RequestBody ItemCarrinhoForm form) {

        Produto produto = produtoService.buscarPorId(form.getProdutoId());

        /* Verificar se a quantidade solicitada está disponível no estoque */
        if (produto.getEstoque() < form.getQuantidade()) {
            throw new EstoqueInsuficienteException(produto.getNome(), form.getQuantidade(), produto.getEstoque());
        }

        /* Criar o item no carrinho, após validação */
        ItemCarrinho item = form.toModel(produto);

        /* Atualizar o estoque do produto */
        produto.setEstoque(produto.getEstoque() - form.getQuantidade());

        ItemCarrinho adicionado = service.adicionarItem(carrinhoId, item);

        return ResponseEntity.ok(new ItemCarrinhoDTO(adicionado));
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Editar item no carrinho", description = "Atualiza os dados de um item existente no carrinho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados."),
            @ApiResponse(responseCode = "404", description = "Item não encontrado.")})
    public ResponseEntity<ItemCarrinhoDTO> editar(
            @PathVariable Long carrinhoId,
            @PathVariable Long itemId,
            @RequestBody ItemCarrinhoForm form) {
        Produto produto = produtoService.buscarPorId(form.getProdutoId());
        ItemCarrinho novosDados = form.toModel(produto);
        ItemCarrinho atualizado = service.editarItem(carrinhoId, itemId, novosDados);
        return ResponseEntity.ok(new ItemCarrinhoDTO(atualizado));
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Deletar item do carrinho", description = "Remove um item de um carrinho, alterando seu status para 'removido'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Item não encontrado.")})
    public ResponseEntity<Void> deletar(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        service.removerItem(carrinhoId, itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar itens de um carrinho", description = "Retorna todos os itens de um carrinho.")
    @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso.")
    public ResponseEntity<List<ItemCarrinhoDTO>> listar(@PathVariable Long carrinhoId) {
        List<ItemCarrinhoDTO> itens = service.listarItens(carrinhoId)
                .stream()
                .map(ItemCarrinhoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "Visualizar item no carrinho", description = "Retorna o item específico do carrinho pelo ID do item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado."),
            @ApiResponse(responseCode = "404", description = "Item não encontrado.")})
    public ResponseEntity<ItemCarrinhoDTO> visualizarItem(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        ItemCarrinho item = service.buscarItemPorId(carrinhoId, itemId);
        return ResponseEntity.ok(new ItemCarrinhoDTO(item));
    }
}
