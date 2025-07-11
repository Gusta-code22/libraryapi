package io.github.Gusta_code22.libraryapi.repository;

import io.github.Gusta_code22.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.Gusta_code22.libraryapi.controller.mappers.LivroMapper;
import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.model.Livro;
import io.github.Gusta_code22.libraryapi.model.generoLivro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {
    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroMapper mapper;
    @Test
    public void salvarTest(){
//        Livro livro = new Livro();
//        livro.setGenero(generoLivro.BIOGRAFIA);
//        livro.setTitulo("Hoje n");
//        livro.setIsbn("Minecraft");
//        livro.setData_publicacao(LocalDate.of(2000,8,1));
//        livro.setPreco(BigDecimal.valueOf(100));

        CadastroLivroDTO dto = new CadastroLivroDTO("978-0-06-112008-4", "O Sol é para Todos"
        , LocalDate.of(1984,01,02), generoLivro.FICCAO, BigDecimal.valueOf(100),
                UUID.fromString("bd24aae2-8240-4318-a9e6-26b1a70cbc8e"));

        Livro livro = mapper.toEntity(dto);
//        // id de um autor ja criado
//        Autor autor = autorRepository
//                 .findById(UUID.fromString("bd24aae2-8240-4318-a9e6-26b1a70cbc8e"))
//                .orElse(null);
        var id1 = repository.findById(livro.getId()).get().getId();
        System.out.println(id1);
        livro.getAutor().getId();
        System.out.println("Livro antes de salvar: " +livro);
        repository.save(livro);



    }
    @Test
    void salvarAutorELivroTest(){
        // salvar um livro
        Livro livro = new Livro();
        livro.setGenero(generoLivro.FICCAO);
        livro.setTitulo("A Sutil arte");
        livro.setIsbn("Essencial");
        livro.setData_publicacao(LocalDate.of(2010,8,1));
        livro.setPreco(BigDecimal.valueOf(109));

        // salvar um altor novo
        Autor autor = new Autor();
        autor.setData_nascimento(LocalDate.of(2010, 3, 20));
        autor.setNome("Qeitor");
        autor.setNacionalidade("Brasil");

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }
    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setGenero(generoLivro.FICCAO);
        livro.setTitulo("O POder do Hábito");
        livro.setIsbn("Essencial");
        livro.setData_publicacao(LocalDate.of(2010,8,1));
        livro.setPreco(BigDecimal.valueOf(109));

        Autor autor = new Autor();
        autor.setData_nascimento(LocalDate.of(2010, 3, 20));
        autor.setNome("Uicas");
        autor.setNacionalidade("Brasil");

        livro.setAutor(autor);


        repository.save(livro);

    }

    @Test
    void atualizarIsbn() {
        var id = UUID.fromString("58c6c46a-6895-4b4a-904a-3ed11840a28e");
        Optional<Livro> possivelLivro = repository.findById(id);

        if (possivelLivro.isPresent()) {
            Livro livroEncontrado = possivelLivro.get();

            livroEncontrado.setIsbn("9080-80");

            repository.save(livroEncontrado);
        }
    }
    @Test
    void atualizarTitulo(){
        var id = UUID.fromString("58c6c46a-6895-4b4a-904a-3ed11840a28e");
        Optional<Livro> possivelLivro = repository.findById(id);

        if (possivelLivro.isPresent()){
            Livro livroEncontrado = possivelLivro.get();

            livroEncontrado.setTitulo("Aprendendo Spring boot");

            repository.save(livroEncontrado);
        }
    }
    @Test
    void atualizarAutor(){
        var id = UUID.fromString("cf90a8b6-ebd9-45ad-a9c9-63f15e081298");
        Optional<Livro> possivelLivro = repository.findById(id);

        if (possivelLivro.isPresent()){
            Livro livroEncontrado = possivelLivro.get();

            Autor autor = autorRepository.findById(UUID.fromString("7f0fd658-e76c-4c62-a0f2-5d8f2cb04792")).orElse(null);
            livroEncontrado.setAutor(autor);
            autorRepository.save(autor);
            repository.save(livroEncontrado);
        }


    }
    @Test

    public void listarTudo(){
        List<Livro> livros = repository.findAll();
        livros.forEach(System.out::println);
    }

    @Test
    void contarLivro(){
        System.out.println("Contagem de Livros " + repository.count());
    }

    @Test
    void deletar(){
        var id = UUID.fromString("4faad1e5-bc83-42d0-af07-ec920ab140e2");
        repository.deleteById(id);
    }

    @Test
    void deleteEntity(){
        var id = UUID.fromString("4faad1e5-bc83-42d0-af07-ec920ab140e2");
        Livro livro = repository.findById(id).get();
        repository.delete(livro);
    }
    @Test
    void buscarTest(){
        var id = UUID.fromString("3381a13a-1bb7-422d-b62a-f60d96607cd7");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        assert livro != null;
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());

    }

    @Test
    void buscarLivroPortitulo(){
        List<Livro> lista = repository.findByTitulo("Kuronami");
        lista.forEach(System.out::println);

    }

    @Test
    void buscarLivroPorIsbn(){
        Optional<Livro> livro = repository.findByIsbn("Valorant");
        if (livro.isPresent()){
            System.out.println(livro);
        }
    }

    @Test
    void buscarLivroPorTituloPreco(){
        String LivroTitulo = "Kuronami";
        var preco = BigDecimal.valueOf(100.00);

        List<Livro> lista = repository.findByTituloAndPreco(LivroTitulo, preco);
        lista.forEach(System.out::println);

    }

    @Test
    void buscarLivroPorTituloOuIsbn(){
        List<Livro> lista = repository.findByTituloOrIsbn("Kuronami", "verdade");
        lista.forEach(System.out::println);

    }

    @Test
    void listarLivrosComQueryJpql(){
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }
    @Test
    void listarAutoresDosLivros(){
        var listarAutores = repository.listarAutoresLivros();
        listarAutores.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidoDosLivros(){
        var lista = repository.listarNomesDiferentLivros();
        lista.forEach(System.out::println);
    }

    @Test
    void listarGeneroDeLivrosAutoresBrasileiro(){
        var listarGuenero = repository.listarGeneroAutoresBrasileiros();
        listarGuenero.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var lista = repository.findByGenero(generoLivro.MISTERIO, "preco");
        lista.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParamTest(){
        var lista = repository.findByGeneroPosicionalParametry(generoLivro.MISTERIO, "preco");
        lista.forEach(System.out::println);
    }

    @Test
    void buscarTituloParecidoNamedParamTest(){
        var lista = repository.findByTituloParecido("ku");
        lista.forEach(System.out::println);
    }

    @Test
    void buscarParecidoPositionalParamTest(){
        var lista = repository.findByIsbnParecido("90");
        lista.forEach(System.out::println);
    }

    // delete
    @Test
    void deletePorGeneroTest(){
        repository.deleteByGenero(generoLivro.BIOGRAFIA);
    }

    @Test
    void deletePorIdQueryTest(){
        repository.deleteByIdQuery(UUID.fromString("05589a7a-58f3-43a6-9555-e4d8a607dd7f"));
    }

    @Test
    void deletePorIsbnTest(){
        repository.deleteByIsbn("90909");
    }

    @Test
    void deletePorPreco(){
        repository.deleteByPreco(BigDecimal.valueOf(109));
    }

    // update
    @Test
    void updateData_Publicacao(){
        repository.updateDataPublicacao(LocalDate.of(2025,4,21), UUID.fromString("992e28ab-9a61-445f-a125-95dacbdf571a"));
    }

    @Test
    void updateIsbnPorIDTest(){
        repository.updateIsbnPorID("Hoje nao vem", UUID.fromString("3381a13a-1bb7-422d-b62a-f60d96607cd7"));
    }

    @Test
    void updateTituloQueryTEst(){
        repository.updateTituloFromId("Kuronami", UUID.fromString("cf90a8b6-ebd9-45ad-a9c9-63f15e081298"));
    }

    @Test
    void updatePrecoQueryTest(){
        repository.updateLivrosComPreco(BigDecimal.valueOf(250), BigDecimal.valueOf(109));
    }






}
