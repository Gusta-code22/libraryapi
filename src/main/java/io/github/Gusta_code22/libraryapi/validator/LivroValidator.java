package io.github.Gusta_code22.libraryapi.validator;

import io.github.Gusta_code22.libraryapi.exceptions.CampoInvalidoException;
import io.github.Gusta_code22.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.Gusta_code22.libraryapi.exceptions.RegistoDuplicadoException;
import io.github.Gusta_code22.libraryapi.model.Livro;
import io.github.Gusta_code22.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Regras:
 * - Se a data de publicação for a partir de 2020, o preço deve ser informado obrigatoriamente.
 * - Não pode haver duplicidade de ISBN.
 */
@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;

    public void validar(Livro livro) {
        if (livroPublicadoAPartirDe2020SemPreco(livro)) {
            throw new CampoInvalidoException("preco", "Preço deverá ser informado para livros publicados a partir de 2020.");
        }
        if (existeLivroComIsbn(livro)) {
            throw new RegistoDuplicadoException("ISBN já cadastrado!");
        }
    }

    private boolean livroPublicadoAPartirDe2020SemPreco(Livro livro) {
        return livro.getData_publicacao() != null &&
                livro.getData_publicacao().isAfter(LocalDate.of(2019, 12, 31)) &&
                livro.getPreco() == null;
    }

    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        // Caso seja novo (sem ID), apenas verifica se já existe algum com mesmo ISBN
        if (livro.getId() == null) {
            return livroEncontrado.isPresent();
        }

        // Se estiver editando, o ISBN não pode pertencer a outro livro
        return livroEncontrado
                .map(Livro::getId)
                .filter(idEncontrado -> !idEncontrado.equals(livro.getId()))
                .isPresent();
    }
}
