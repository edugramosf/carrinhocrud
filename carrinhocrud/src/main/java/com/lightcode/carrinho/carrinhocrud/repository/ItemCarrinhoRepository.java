package com.lightcode.carrinho.carrinhocrud.repository;

import com.lightcode.carrinho.carrinhocrud.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
}
