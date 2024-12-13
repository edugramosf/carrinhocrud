package com.lightcode.carrinho.carrinhocrud.controller;

import com.lightcode.carrinho.carrinhocrud.model.Produto;
import com.lightcode.carrinho.carrinhocrud.service.ProdutoService;
import com.lightcode.carrinho.carrinhocrud.dto.ProdutoDTO;
import com.lightcode.carrinho.carrinhocrud.form.ProdutoForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo produto", description = "Cria um produto com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação de dados.")})
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody @Valid ProdutoForm produtoForm) {
        Produto produto = produtoService.salvar(produtoForm.toModel());
        return ResponseEntity.status(201).body(new ProdutoDTO(produto));
    }
}
