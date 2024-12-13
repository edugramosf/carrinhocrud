package com.lightcode.carrinho.carrinhocrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "O nome do cliente não pode ser nulo.")
    private String clienteNome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "O status do carrinho não pode ser nulo.")
    private StatusCarrinho status;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;
}
