package io.github.Gusta_code22.libraryapi.service;

import io.github.Gusta_code22.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.Gusta_code22.libraryapi.model.Livro;
import io.github.Gusta_code22.libraryapi.model.Usuario;
import io.github.Gusta_code22.libraryapi.model.generoLivro;
import io.github.Gusta_code22.libraryapi.repository.AutorRepository;
import io.github.Gusta_code22.libraryapi.repository.LivroRepository;
import io.github.Gusta_code22.libraryapi.repository.specs.LivroSpecs;
import io.github.Gusta_code22.libraryapi.security.SecurityService;
import io.github.Gusta_code22.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.Gusta_code22.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    private final AutorRepository autorRepository;

    public Livro salvar(Livro livro) {

        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario.getId());
        return livroRepository.save(livro);
    }

    public Optional<Livro> buscarPorId(UUID id){
        return livroRepository.findById(id);
    }


    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public void atualizarLivro(Livro livro){
        if (livro.getId() == null){
            throw new OperacaoNaoPermitidaException("Id nao pode ser nulo em uma atualizacao");
        }



        validator.validar(livro);

        livroRepository.save(livro);
    }

    public Page<Livro> pesquisa(
            String isbn, String titulo, String nomeAutor, generoLivro genero, Integer anoPublicacao,
            Integer pagina, Integer tamanhoPagina){

//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero));
        Specification<Livro> specs = Specification.where(conjuction());

        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if (anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        Pageable pageablerequest = PageRequest.of(pagina, tamanhoPagina);
        return livroRepository.findAll(specs, pageablerequest);
    }

    public void deletarPorId(UUID id) {
        livroRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return livroRepository.existsById(id);
    }
}
