package com.lightcode.carrinho.carrinhocrud.service;

import com.lightcode.carrinho.carrinhocrud.exception.EstoqueInsuficienteException;
import com.lightcode.carrinho.carrinhocrud.model.*;
import com.lightcode.carrinho.carrinhocrud.repository.CarrinhoRepository;
import com.lightcode.carrinho.carrinhocrud.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ItemCarrinhoServiceTest {

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Test
    public void naoDeveSalvarItemComQuantidadeMaiorQueEstoque() {
        Produto produto = new Produto();
        produto.setNome("Monitor LG");
        produto.setDescricao("Monitor UltraWide 29 polegadas");
        produto.setPreco(new BigDecimal("1500.00"));
        produto.setEstoque(1); // Estoque insuficiente
        produtoRepository.save(produto);

        Carrinho carrinho = new Carrinho();
        carrinho.setClienteNome("Maria");
        carrinho.setStatus(StatusCarrinho.ATIVO);
        carrinhoRepository.save(carrinho);

        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setCarrinho(carrinho);
        itemCarrinho.setProduto(produto);
        itemCarrinho.setQuantidade(2); // Maior que o estoque disponível

        assertThrows(EstoqueInsuficienteException.class, () -> {
            itemCarrinhoService.adicionarItem(carrinho.getId(), itemCarrinho);
        });
    }
}
