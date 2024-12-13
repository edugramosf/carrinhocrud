package com.lightcode.carrinho.carrinhocrud.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarrinhoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarListaDeCarrinhos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/carrinhos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deveCriarCarrinho() throws Exception {
        String novoCarrinho = """
            {
                "clienteNome": "Maria"
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/carrinhos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novoCarrinho))
                .andExpect(status().isCreated());
    }
}

