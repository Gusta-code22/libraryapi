package io.github.Gusta_code22.libraryapi.serice;

import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.model.Livro;
import io.github.Gusta_code22.libraryapi.model.generoLivro;
import io.github.Gusta_code22.libraryapi.repository.AutorRepository;
import io.github.Gusta_code22.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
/**
 * @see transacoesTest
 */


@Service
public class TransacaoService {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){
        Livro livro = livroRepository.
                findById(UUID.fromString("13364369-acdc-4390-89f6-fd4d5624e678")).orElse(null);
        livro.setData_publicacao(LocalDate.of(2024, 1, 6));

    }

    @Transactional
    public void executar(){
        // salvar um altor novo
        Autor autor = new Autor();
        autor.setData_nascimento(LocalDate.of(2010, 3, 20));
        autor.setNome("Francisco");
        autor.setNacionalidade("Brasil");

        autorRepository.saveAndFlush(autor);



        // salvar um livro
        Livro livro = new Livro();
        livro.setGenero(generoLivro.FICCAO);
        livro.setTitulo("Teste francisco");
        livro.setIsbn("Essencial");
        livro.setData_publicacao(LocalDate.of(2010,8,1));
        livro.setPreco(BigDecimal.valueOf(109));

        livro.setAutor(autor);

        livroRepository.saveAndFlush(livro);
        if (autor.getNome().equals("Francisco")){
            throw new RuntimeException("Rollback!");
        }
    }
}
