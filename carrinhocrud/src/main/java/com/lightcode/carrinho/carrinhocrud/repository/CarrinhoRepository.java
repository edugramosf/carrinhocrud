package com.lightcode.carrinho.carrinhocrud.repository;

import com.lightcode.carrinho.carrinhocrud.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
