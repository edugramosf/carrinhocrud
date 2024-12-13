package com.lightcode.carrinho.carrinhocrud.service;

import com.lightcode.carrinho.carrinhocrud.model.Carrinho;
import com.lightcode.carrinho.carrinhocrud.model.StatusCarrinho;
import com.lightcode.carrinho.carrinhocrud.repository.CarrinhoRepository;
import com.lightcode.carrinho.carrinhocrud.exception.CarrinhoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private static final Logger logger = LoggerFactory.getLogger(CarrinhoService.class);

    private final CarrinhoRepository repository;

    public Carrinho criar(Carrinho carrinho) {
        logger.info("Criando novo carrinho para o cliente: {}", carrinho.getClienteNome());
        logger.debug("Carrinho inicial: {}", carrinho);

        carrinho.setStatus(StatusCarrinho.ATIVO);
        carrinho.setCriadoEm(LocalDateTime.now());
        carrinho.setAtualizadoEm(LocalDateTime.now());

        Carrinho salvo = repository.save(carrinho);
        logger.debug("Carrinho salvo com ID: {}", salvo.getId());
        return salvo;
    }

    public List<Carrinho> listar() {
        logger.info("Listando todos os carrinhos.");
        return repository.findAll();
    }

    public Page<Carrinho> listarComPaginacao(Pageable pageable) {
        logger.info("Listando todos os carrinhos com paginação.");
        return repository.findAll(pageable);
    }

    public Carrinho buscarPorId(Long id) {
        logger.info("Buscando carrinho com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new CarrinhoNotFoundException("Carrinho não encontrado com o ID: " + id));
    }

    public Carrinho editar(Long id, Carrinho novosDados) {
        logger.info("Editando carrinho com ID: {}", id);
        validarDados(novosDados);

        Carrinho carrinhoExistente = buscarPorId(id);

        if (novosDados.getItens().stream().anyMatch(item -> item.getQuantidade() <= 0)) {
            throw new IllegalArgumentException("A quantidade dos itens deve ser maior que zero.");
        }

        carrinhoExistente.setClienteNome(novosDados.getClienteNome());
        carrinhoExistente.setItens(novosDados.getItens());
        carrinhoExistente.setAtualizadoEm(LocalDateTime.now());

        Carrinho atualizado = repository.save(carrinhoExistente);
        logger.debug("Carrinho atualizado: {}", atualizado);
        return atualizado;
    }

    public void deletar(Long id) {
        logger.info("Deletando (soft delete) carrinho com ID: {}", id);
        Carrinho carrinho = buscarPorId(id);
        softDelete(carrinho);
    }

    public Carrinho ativarCarrinho(Long id) {
        logger.info("Ativando carrinho com ID: {}", id);
        Carrinho carrinho = buscarPorId(id);
        carrinho.setStatus(StatusCarrinho.ATIVO);
        carrinho.setAtualizadoEm(LocalDateTime.now());
        return repository.save(carrinho);
    }

    public Carrinho inativarCarrinho(Long id) {
        logger.info("Inativando carrinho com ID: {}", id);
        Carrinho carrinho = buscarPorId(id);
        carrinho.setStatus(StatusCarrinho.INATIVO);
        carrinho.setAtualizadoEm(LocalDateTime.now());
        return repository.save(carrinho);
    }

    private void softDelete(Carrinho carrinho) {
        carrinho.setStatus(StatusCarrinho.INATIVO);
        carrinho.setAtualizadoEm(LocalDateTime.now());
        repository.save(carrinho);
        logger.info("Carrinho marcado como INATIVO. ID: {}", carrinho.getId());
    }

    private void validarDados(Carrinho carrinho) {
        if (carrinho.getClienteNome() == null || carrinho.getClienteNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio.");
        }
        if (carrinho.getItens() == null) {
            throw new IllegalArgumentException("O carrinho deve conter uma lista de itens (mesmo que vazia).");
        }
    }
}