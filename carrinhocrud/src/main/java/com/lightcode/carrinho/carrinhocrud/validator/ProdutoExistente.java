package com.lightcode.carrinho.carrinhocrud.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProdutoExistenteValidator.class) // Vincula ao Validator
@Target({ ElementType.FIELD, ElementType.PARAMETER }) // Define onde pode ser usada
@Retention(RetentionPolicy.RUNTIME) // Disponível em tempo de execução
public @interface ProdutoExistente {
    String message() default "O produto informado não existe."; // Mensagem padrão

    Class<?>[] groups() default {}; // Grupos de validação (opcional)

    Class<? extends Payload>[] payload() default {}; // Metadados adicionais (opcional)
}
