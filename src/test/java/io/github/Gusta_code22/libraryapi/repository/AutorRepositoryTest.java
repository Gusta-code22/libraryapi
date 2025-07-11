package io.github.Gusta_code22.libraryapi.repository;

import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.model.Livro;
import io.github.Gusta_code22.libraryapi.model.generoLivro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest

public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void exemple(){
        Autor autor = new Autor();
        autor.setData_nascimento(LocalDate.of(2010, 3, 20));
        autor.setNome("ola");
        autor.setNacionalidade("Brasil");

        var autorSalvo = autorRepository.save(autor);

    }

    @Test
    public void AtualizarData(){
        var id = UUID.fromString("7f0fd658-e76c-4c62-a0f2-5d8f2cb04792");
        Optional<Autor> possivelautor = autorRepository.findById(id);

        if (possivelautor.isPresent()){
            Autor autorEncontrado = possivelautor.get();

            System.out.println("Autor encontrado");
            System.out.println(autorEncontrado);

            autorEncontrado.setData_nascimento(LocalDate.of(200, 9 ,1));
            autorEncontrado.setNome("Gustavo");

            autorRepository.save(autorEncontrado);
        }
    }

//    @Test
//    public void AtualizarNome(){
//        var nome = "Gualhardo";
//        List<Autor> possivelAutor = autorRepository.findByNome(nome);
//
//        if (possivelAutor.isPresent()){
//            Autor autorEncontrado = possivelAutor.get();
//
//            autorEncontrado.setNome("Gustavo");
//
//            autorRepository.save(autorEncontrado);
//        }
//    }
//
//    @Test
//    public void atualizarNacionalidade(){
//        var nacionalidade = "BRASIL";
//        List<Autor> possivelAutor = autorRepository.findByNacionalidade(nacionalidade);
//
//        if (possivelAutor.isPresent()){
//            Autor autorEncontrado = possivelAutor.get();
//
//            autorEncontrado.setNacionalidade("Brasil");
//            autorRepository.save(autorEncontrado);
//        }
//    }

    @Test
    public void listarTudo(){
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void contarAutoresTest(){
        System.out.println("Contagem de autores " + autorRepository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("d23d9ce5-5cf4-4e3d-bafd-a7993ce50620");
        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();

            autorRepository.deleteById(id);
        }
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("c026a0a2-b4c0-433f-873b-0d08f39f3a4e");
        Optional<Autor> possivelautor = autorRepository.findById(id);
        if (possivelautor.isPresent()){
            var Idemar = autorRepository.findById(id).get();
            autorRepository.delete(Idemar);
        }
    }
    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setData_nascimento(LocalDate.of(1970, 3, 20));
        autor.setNome("Gustavo");
        autor.setNacionalidade("Portugual");

        Livro livro = new Livro();
        livro.setGenero(generoLivro.MISTERIO);
        livro.setTitulo("O portuges");
        livro.setIsbn("9090549045390453-34");
        livro.setData_publicacao(LocalDate.of(2000,8,1));
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setGenero(generoLivro.FICCAO);
        livro2.setTitulo("O ouro robado");
        livro2.setIsbn("verdade");
        livro2.setData_publicacao(LocalDate.of(2000,8,1));
        livro2.setPreco(BigDecimal.valueOf(100));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void salvarAutorComLivrosCascadeTest(){
        Autor autor = new Autor();
        autor.setData_nascimento(LocalDate.of(1970, 3, 20));
        autor.setNome("Javeiro");
        autor.setNacionalidade("Portugual");

        Livro livro = new Livro();
        livro.setGenero(generoLivro.MISTERIO);
        livro.setTitulo("O cara que traiu Python");
        livro.setIsbn("90453-34");
        livro.setData_publicacao(LocalDate.of(2010,8,1));
        livro.setPreco(BigDecimal.valueOf(10000));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setGenero(generoLivro.BIOGRAFIA);
        livro2.setTitulo("Fez o certo");
        livro2.setIsbn("verdade");
        livro2.setData_publicacao(LocalDate.of(2000,8,1));
        livro2.setPreco(BigDecimal.valueOf(1000));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros());

    }

    @Test
    void listarAutorComLivros(){
        var id = UUID.fromString("7976b349-edb0-4c92-8194-dcb2a13aaa6a");
        var autor = autorRepository.findById(id).get();
        List<Livro> listaLivros = livroRepository.findByAutor(autor);
        autor.setLivros(listaLivros);

        autor.getLivros().forEach(System.out::println);
    }

    @Test
    void listarAutorNomeNacionalidade(){
        List<Autor> autores = autorRepository.findByNomeAndNacionalidade("Gusteivo", "Brasil");
        autores.forEach(System.out::println);
    }



}
