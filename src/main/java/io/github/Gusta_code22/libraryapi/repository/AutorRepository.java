package io.github.Gusta_code22.libraryapi.repository;

import io.github.Gusta_code22.libraryapi.controller.dto.AutorDTO;
import io.github.Gusta_code22.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see AutorRepositoryTest
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID>, JpaSpecificationExecutor<Autor> {
    List<Autor> findByNome(String nome);

    List<Autor> findByNacionalidade(String nacionalidade);

    @Query("select a from Autor as a where a.nome = ?1 or a.nacionalidade = ?2")
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

//    @Query("select a from Autor as a where l.Nome")

    @Query("select a from Autor as a where a.nome = ?1 and a.data_nascimento = ?2 and a.nacionalidade = ?3")
    Optional<Autor> buscaPorNomeDataNascimentoENacionalidade(
            String nome, LocalDate data_nascimento, String nacionalidade
    );

    boolean existsByNome(String nome);

    boolean existsByNacionalidade(String nacionalidade);

    boolean existsByNomeAndNacionalidade(String nome, String nacionalidade);
}
