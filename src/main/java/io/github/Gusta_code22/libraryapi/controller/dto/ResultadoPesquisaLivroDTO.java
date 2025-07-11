package io.github.Gusta_code22.libraryapi.controller.dto;

import io.github.Gusta_code22.libraryapi.model.generoLivro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "ResultadoPesquisaLivro")
public record ResultadoPesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate data_publicacao,
        generoLivro genero,
        BigDecimal preco,
        AutorDTO autor
) {

}
