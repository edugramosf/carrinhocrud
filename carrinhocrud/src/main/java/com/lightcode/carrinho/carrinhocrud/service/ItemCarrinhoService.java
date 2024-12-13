package com.lightcode.carrinho.carrinhocrud.service;

import com.lightcode.carrinho.carrinhocrud.exception.CarrinhoNotFoundException;
import com.lightcode.carrinho.carrinhocrud.exception.EstoqueInsuficienteException;
import com.lightcode.carrinho.carrinhocrud.exception.ItemNotFoundException;
import com.lightcode.carrinho.carrinhocrud.model.Carrinho;
import com.lightcode.carrinho.carrinhocrud.model.ItemCarrinho;
import com.lightcode.carrinho.carrinhocrud.model.Produto;
import com.lightcode.carrinho.carrinhocrud.model.StatusItem;
import com.lightcode.carrinho.carrinhocrud.repository.CarrinhoRepository;
import com.lightcode.carrinho.carrinhocrud.repository.ItemCarrinhoRepository;
import com.lightcode.carrinho.carrinhocrud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCarrinhoService {

    private static final Logger logger = LoggerFactory.getLogger(ItemCarrinhoService.class);

    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemCarrinho adicionarItem(Long carrinhoId, ItemCarrinho item) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new CarrinhoNotFoundException("Carrinho não encontrado com o ID: " + carrinhoId));

        Produto produto = produtoRepository.findById(item.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + item.getProduto().getId()));

        if (produto.getEstoque() < item.getQuantidade()) {
            throw new EstoqueInsuficienteException(
                    produto.getNome(),
                    item.getQuantidade(),
                    produto.getEstoque()
            );
        }

        produto.setEstoque(produto.getEstoque() - item.getQuantidade());
        produtoRepository.save(produto);

        item.setCarrinho(carrinho);
        item.setProduto(produto);
        item.setStatus(StatusItem.ATIVO);
        item.setCriadoEm(LocalDateTime.now());
        item.setAtualizadoEm(LocalDateTime.now());

        carrinho.getItens().add(item);

        carrinhoRepository.save(carrinho);

        return itemCarrinhoRepository.save(item);
    }

    public List<ItemCarrinho> listarItens(Long carrinhoId) {
        logger.info("Listando itens do carrinho com ID: {}", carrinhoId);
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new CarrinhoNotFoundException("Carrinho não encontrado com o ID: " + carrinhoId));
        return carrinho.getItens();
    }

    public ItemCarrinho editarItem(Long carrinhoId, Long itemId, ItemCarrinho novosDados) {
        logger.info("Editando item com ID: {} no carrinho com ID: {}", itemId, carrinhoId);
        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item não encontrado com o ID: " + itemId));

        if (!item.getCarrinho().getId().equals(carrinhoId)) {
            throw new RuntimeException("Item não pertence ao carrinho especificado.");
        }

        if (item.getProduto().getEstoque() + item.getQuantidade() < novosDados.getQuantidade()) {
            throw new EstoqueInsuficienteException(item.getProduto().getNome(), novosDados.getQuantidade(), item.getProduto().getEstoque());
        }

        item.getProduto().setEstoque(item.getProduto().getEstoque() + item.getQuantidade() - novosDados.getQuantidade());
        item.setQuantidade(novosDados.getQuantidade());
        item.setAtualizadoEm(LocalDateTime.now());

        return itemCarrinhoRepository.save(item);
    }

    public void removerItem(Long carrinhoId, Long itemId) {
        logger.info("Removendo item com ID: {} do carrinho com ID: {}", itemId, carrinhoId);
        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item não encontrado com o ID: " + itemId));
        if (!item.getCarrinho().getId().equals(carrinhoId)) {
            throw new RuntimeException("Item não pertence ao carrinho especificado.");
        }

        item.setStatus(StatusItem.REMOVIDO);
        item.setAtualizadoEm(LocalDateTime.now());
        itemCarrinhoRepository.save(item);
        logger.info("Item com ID: {} removido do carrinho com sucesso.", itemId);
    }

    public ItemCarrinho buscarItemPorId(Long carrinhoId, Long itemId) {
        logger.info("Buscando item com ID: {} no carrinho com ID: {}", itemId, carrinhoId);
        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item não encontrado com o ID: " + itemId));

        if (!item.getCarrinho().getId().equals(carrinhoId)) {
            throw new RuntimeException("Item não pertence ao carrinho especificado.");
        }
        return item;
    }
}
