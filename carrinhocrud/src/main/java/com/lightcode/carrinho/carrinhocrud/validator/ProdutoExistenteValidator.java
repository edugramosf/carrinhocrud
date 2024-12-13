package com.lightcode.carrinho.carrinhocrud.validator;

import com.lightcode.carrinho.carrinhocrud.repository.ProdutoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ProdutoExistenteValidator implements ConstraintValidator<ProdutoExistente, Long> {

    private final ProdutoRepository produtoRepository;

    public ProdutoExistenteValidator(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public boolean isValid(Long produtoId, ConstraintValidatorContext context) {
        if (produtoId == null) {
            return false;
        }
        return produtoRepository.existsById(produtoId);
    }
}
