package io.github.Gusta_code22.libraryapi.repository.specs;

import io.github.Gusta_code22.libraryapi.model.Livro;
import io.github.Gusta_code22.libraryapi.model.generoLivro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return ((root, query, cb) -> cb.equal(root.get("isbn"), isbn));

    }

    public static Specification<Livro> tituloLike(String titulo){
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(generoLivro genero){
        return (root, query, cb) ->
                cb.equal(root.get("genero"), genero);

    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor){
        return (root, query, cb) ->
//                cb.like(cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        };
    }

    public static Specification<Livro> conjuction(){
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
//        and to_char(data_publicacao, 'YYYY') =  :anoPublicacao
        return (root, query, cb) -> cb.equal(cb.function(
                "to_char", String.class, root.get("data_publicacao"), cb.literal("YYYY")
        ), anoPublicacao.toString());
    }
}
