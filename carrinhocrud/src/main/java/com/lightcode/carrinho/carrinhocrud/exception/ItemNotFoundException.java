package com.lightcode.carrinho.carrinhocrud.exception;

public class ItemNotFoundException extends RuntimeException {
  public ItemNotFoundException(String message) {
    super(message);
  }
}