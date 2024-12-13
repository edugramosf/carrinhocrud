package com.lightcode.carrinho.carrinhocrud.service;

import com.lightcode.carrinho.carrinhocrud.model.Produto;
import com.lightcode.carrinho.carrinhocrud.model.StatusProduto;
import com.lightcode.carrinho.carrinhocrud.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto buscarPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        if (produto.getStatus() == StatusProduto.INATIVO) {
            throw new RuntimeException("Produto não disponível para venda. Status: INATIVO.");
        }

        return produto;
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }
}
