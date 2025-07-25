package io.github.Gusta_code22.libraryapi.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatorio")
        @Size(max = 100, min = 2, message = "Campo fora do tamanho padrao")
        @Schema(name = "nome")
        String nome,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "nao pode ser uma data futura")
        @Schema(name = "dataNascimento")
        LocalDate data_nascimento,
        @NotBlank(message = "campo obrigatorio")
        @Size(max = 50, min = 2, message = "Campo fora do tamanho padrao")
        @Schema(name = "nacionalidade")
        String nacionalidade
) {
}
