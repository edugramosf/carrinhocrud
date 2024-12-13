package com.lightcode.carrinho.carrinhocrud.exception;

import lombok.Getter;

@Getter
public class CarrinhoNotFoundException extends RuntimeException {
    public CarrinhoNotFoundException(String message) {
        super(message);
    }
}
