package com.lightcode.carrinho.carrinhocrud.form;

import com.lightcode.carrinho.carrinhocrud.model.Carrinho;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class CarrinhoForm {
    @NotBlank(message = "O nome do cliente é obrigatório.")
    @Size(max = 100, message = "O nome do cliente não pode ter mais de 100 caracteres.")
    private String clienteNome;

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public Carrinho toModel() {
        Carrinho carrinho = new Carrinho();
        carrinho.setClienteNome(this.clienteNome);
        return carrinho;
    }
}
