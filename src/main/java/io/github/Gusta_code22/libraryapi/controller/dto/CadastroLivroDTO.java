package io.github.Gusta_code22.libraryapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.Gusta_code22.libraryapi.model.generoLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Schema(name = "CadastroLivro")
public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo obrigatorio")
        String isbn,
        @NotBlank(message = "campo obrigatorio")
        String titulo,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "nao pode ser uma data futura")
        @JsonProperty("data_publicacao")
        LocalDate data_publicacao,
        generoLivro genero,
        BigDecimal preco,
        @NotNull(message = "campo obrigatorio")
        UUID id_autor
) {
}
