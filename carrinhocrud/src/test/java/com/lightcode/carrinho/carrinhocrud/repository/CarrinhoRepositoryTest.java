package com.lightcode.carrinho.carrinhocrud.repository;

import com.lightcode.carrinho.carrinhocrud.model.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class CarrinhoRepositoryTest {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Test
    public void deveSalvarCarrinhoNoBanco() {
        Carrinho carrinho = new Carrinho();
        carrinho.setClienteNome("João");
        carrinho.setStatus(StatusCarrinho.ATIVO);

        Carrinho salvo = carrinhoRepository.save(carrinho);

        assertNotNull(salvo.getId(), "O ID do carrinho não deveria ser nulo.");
    }

    @Test
    public void deveSalvarCarrinhoComItens() {
        Produto produto = new Produto();
        produto.setNome("Monitor LG");
        produto.setDescricao("Monitor UltraWide 29 polegadas");
        produto.setPreco(new BigDecimal("1500.00"));
        produto.setEstoque(5);
        produtoRepository.save(produto);

        Carrinho carrinho = new Carrinho();
        carrinho.setClienteNome("João");
        carrinho.setStatus(StatusCarrinho.ATIVO);
        carrinhoRepository.save(carrinho);

        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setCarrinho(carrinho);
        itemCarrinho.setProduto(produto);
        itemCarrinho.setQuantidade(2);
        itemCarrinho.setStatus(StatusItem.ATIVO);
        itemCarrinhoRepository.save(itemCarrinho);

        assertNotNull(itemCarrinho.getId(), "O ID do item do carrinho não deveria ser nulo.");
    }
}
