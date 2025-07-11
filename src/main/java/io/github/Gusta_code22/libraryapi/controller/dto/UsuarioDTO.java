package io.github.Gusta_code22.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(name = "Usuario")
public record UsuarioDTO(
        @NotBlank(message = "Campo Obrigatorio")
        String login,

        @Email(message = "Email Invalido")
        @NotBlank(message = "Campo Obrigatorio")
        String email,

        @NotBlank(message = "Campo Obrigatorio")
        String senha,

        @NotBlank(message = "Campo Obrigatorio")
        String provider,

        @NotNull
        List<String> roles) {
}
