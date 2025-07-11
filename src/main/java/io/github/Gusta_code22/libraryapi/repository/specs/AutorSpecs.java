package io.github.Gusta_code22.libraryapi.repository.specs;

import io.github.Gusta_code22.libraryapi.model.Autor;
import org.springframework.data.jpa.domain.Specification;

public class AutorSpecs {

    public static Specification<Autor> nomeLike(String nome) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Autor> nacionalidadeLike(String nacionalidade) {
        return (root, query, cb) -> cb.like(
                cb.upper(root.get("nacionalidade")), "%" + nacionalidade.toUpperCase() + "%"
        );
    }

    public static Specification<Autor> conjuction() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
}


