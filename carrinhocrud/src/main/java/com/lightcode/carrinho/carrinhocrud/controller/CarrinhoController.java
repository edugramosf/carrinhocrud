package com.lightcode.carrinho.carrinhocrud.controller;

import com.lightcode.carrinho.carrinhocrud.dto.CarrinhoDTO;
import com.lightcode.carrinho.carrinhocrud.form.CarrinhoForm;
import com.lightcode.carrinho.carrinhocrud.model.Carrinho;
import com.lightcode.carrinho.carrinhocrud.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrinhos")
@Validated
public class CarrinhoController {

    private final CarrinhoService service;

    public CarrinhoController(CarrinhoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar um novo carrinho", description = "Cria um carrinho com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrinho criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação de dados.")})
    public ResponseEntity<CarrinhoDTO> criar(@Valid @RequestBody CarrinhoForm form) {
        Carrinho carrinho = form.toModel();
        Carrinho criado = service.criar(carrinho);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CarrinhoDTO(criado));
    }

    @GetMapping
    @Operation(summary = "Listar todos os carrinhos", description = "Retorna todos os carrinhos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carrinhos retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")})
    public ResponseEntity<List<CarrinhoDTO>> listar() {
        List<CarrinhoDTO> carrinhos = service.listar()
                .stream()
                .map(CarrinhoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carrinhos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um carrinho por ID", description = "Recupera um carrinho específico pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho encontrado."),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado.")})
    public ResponseEntity<CarrinhoDTO> buscarPorId(@PathVariable Long id) {
        Carrinho carrinho = service.buscarPorId(id);
        return ResponseEntity.ok(new CarrinhoDTO(carrinho));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar um carrinho", description = "Atualiza os dados de um carrinho existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação de dados.")})
    public ResponseEntity<CarrinhoDTO> editar(@PathVariable Long id, @Valid @RequestBody CarrinhoForm form) {
        Carrinho carrinho = form.toModel();
        Carrinho atualizado = service.editar(id, carrinho);
        return ResponseEntity.ok(new CarrinhoDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um carrinho", description = "Remove um carrinho, alterando seu status para 'inativo'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrinho removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado.")})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
