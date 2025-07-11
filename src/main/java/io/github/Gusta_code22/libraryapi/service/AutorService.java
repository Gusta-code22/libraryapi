package io.github.Gusta_code22.libraryapi.service;

import io.github.Gusta_code22.libraryapi.exceptions.FiltroDePesquisaInvalidoException;
import io.github.Gusta_code22.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.model.Usuario;
import io.github.Gusta_code22.libraryapi.repository.AutorRepository;
import io.github.Gusta_code22.libraryapi.repository.LivroRepository;
import io.github.Gusta_code22.libraryapi.security.SecurityService;
import io.github.Gusta_code22.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.Gusta_code22.libraryapi.repository.specs.AutorSpecs.*;

/**
 * @see io.github.Gusta_code22.libraryapi.controller.AutorController
 */
@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    // Post
    public Autor salvar(Autor autor){
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario.getId());
        return autorRepository.save(autor);
    }

    public Autor autualizarAutor(UUID id, Autor autor){
        if (id == null){
            throw new IllegalArgumentException("ID não pode ser nulo na atualização.");
        }
        validator.validar(autor);
        autor.setId(id);

        return autorRepository.save(autor);
    }

    public Optional<Autor> buscarPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deleteAutor(Autor autor){
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("nao é possivel excluir Autor que tem livro cadastrado");

        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            if (nomeENacionalidadeExisteNoBanco(nome, nacionalidade)) {
                return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
            } else {
                throw new FiltroDePesquisaInvalidoException("Nao existe autores com esse nome nem nacionalidade no banco");
            }
        }
        if (nome != null){
            if (nomeAutorRepository(nome)){
                return autorRepository.findByNome(nome);
            }else {
                throw new FiltroDePesquisaInvalidoException("Nao existe autores com esse nome no banco");
            }
        }

        if (nacionalidade != null){
            if (nacionalidadeExisteNoBanco(nacionalidade)) {
                return autorRepository.findByNacionalidade(nacionalidade);
            } else {
                throw new FiltroDePesquisaInvalidoException("Nao existe autor com essa nacionalidade no banco, impossivel pesquisar");
            }
        }

        return autorRepository.findAll();

    }

    public List<Autor> pesquisarByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);
    }

    public Page<Autor> pesquisaBySpecification(String nome, String nacionalidade, Integer pagina, Integer tamanhoPagina){

        Specification<Autor> specs = Specification.where(conjuction());

        if (nome != null){
            specs = specs.and(nomeLike(nome));
        }

        if (nacionalidade != null){
            specs = specs.and(nacionalidadeLike(nacionalidade));
        }

        PageRequest page = PageRequest.of(pagina, tamanhoPagina);
        return autorRepository.findAll(specs, page);
    }
    
    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

    public boolean nomeAutorRepository(String nome){
        return autorRepository.existsByNome(nome);
    }

    public boolean nacionalidadeExisteNoBanco(String nacionalidade) {
        return autorRepository.existsByNacionalidade(nacionalidade);
    }

    public boolean nomeENacionalidadeExisteNoBanco(String nome, String nacionalidade){
        return autorRepository.existsByNomeAndNacionalidade(nome, nacionalidade);
    }

}
