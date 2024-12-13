package com.lightcode.carrinho.carrinhocrud.service;

import com.lightcode.carrinho.carrinhocrud.model.Carrinho;
import com.lightcode.carrinho.carrinhocrud.model.StatusCarrinho;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CarrinhoServiceTest {
    @Autowired
    private CarrinhoService carrinhoService;

    @Test
    public void deveCriarCarrinhoComSucesso() {
        Carrinho carrinho = new Carrinho();
        carrinho.setClienteNome("João");
        carrinho.setStatus(StatusCarrinho.ATIVO);

        Carrinho salvo = carrinhoService.criar(carrinho);
        assertNotNull(salvo.getId());
        assertEquals("João", salvo.getClienteNome());
    }
}
