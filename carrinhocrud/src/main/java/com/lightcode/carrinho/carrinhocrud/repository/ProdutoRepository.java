package com.lightcode.carrinho.carrinhocrud.repository;

import com.lightcode.carrinho.carrinhocrud.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
