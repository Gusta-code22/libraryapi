package io.github.Gusta_code22.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Client")
public record ClientDTO(

        @NotBlank(message = "O clientId é obrigatório.")
        String clientId,

        @NotBlank(message = "O clientSecret é obrigatório.")
        String clientSecret,

        @NotBlank(message = "O redirectURI é obrigatório.")
        String redirectURI,

        @NotBlank(message = "Os scopes são obrigatórios.")
        String scope
        ) {
}
