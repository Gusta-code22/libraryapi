package io.github.Gusta_code22.libraryapi.repository;

import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import io.github.Gusta_code22.libraryapi.model.generoLivro;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * @see LivroRepositoryTest
 * @author PC Gusta
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {



    // Query Method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = ?
    Optional<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);




    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     * select a.*
     * from livro as l
     * join autor a on a.id = l.id_autor

     */
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresLivros();


    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentLivros();

    @Query("select a from Livro l join l.autor a where l.id = ?1")
    Optional<Livro> listarLivros(String id);

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
    """)
    List<String> listarGeneroAutoresBrasileiros();


    // NAMED PARAMETRY
    @Query(" select l from Livro l where l.genero = :genero order by :ParamOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") generoLivro generoLivro,
            @Param("ParamOrdenacao") String nomePropiedade
    );

    // Posicional parametry
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPosicionalParametry(generoLivro generoLivro, String nomePropriedade);

    @Query("select l from Livro l where  l.titulo ILIKE %:titulo%")
    List<Livro> findByTituloParecido(@Param("titulo") String titulo);

    @Query("select l from Livro l where l.isbn ILIKE  %?1% ")
    List<Livro> findByIsbnParecido(String isbn);

    // Updates

    @Modifying
    @Transactional
    @Query("update Livro l set l.data_publicacao = :novaData where l.id = :id") // update nao se utiliza from
    void updateDataPublicacao(
            @Param("novaData") LocalDate novaData,
            @Param("id") UUID id
    );


    @Modifying
    @Transactional
    @Query("update Livro l set l.titulo = :titulo where l.id =:id")
    void updateTituloFromId(
            @Param("titulo") String titulo,
            @Param("id") UUID id
    );

    @Modifying
    @Transactional
    @Query("update Livro l set l.preco = :preco where l.preco = :precoantigo ")
    void updateLivrosComPreco(
            @Param("preco") BigDecimal preco,
            @Param("precoantigo") BigDecimal precoantigo
    );

    @Modifying
    @Transactional
    @Query("update Livro l set l.isbn = :isbn where l.id = :id")
    void updateIsbnPorID(
      @Param("isbn") String isbn,
      @Param("id") UUID id
    );

    // Deletrs
    @Modifying
    @Transactional
    @Query("delete from Livro where titulo = ?1")
    void DeleteByTitulo(String titulo);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")// delete usa-se from e nao tem o as
    void deleteByGenero(generoLivro generoLivro);

    @Modifying
    @Transactional
    @Query("delete from Livro where id = :id")
    void deleteByIdQuery(UUID id);

    @Modifying
    @Transactional
    @Query("delete from Livro where isbn = :isbn")
    void deleteByIsbn(@Param("isbn") String isbn);

    @Modifying
    @Transactional
    @Query("delete from Livro where preco = ?1")
    void deleteByPreco(BigDecimal preco);


    boolean existsByAutor(Autor autor);
}
