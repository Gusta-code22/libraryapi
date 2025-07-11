package io.github.Gusta_code22.libraryapi.controller;

import io.github.Gusta_code22.libraryapi.controller.dto.UsuarioDTO;
import io.github.Gusta_code22.libraryapi.controller.mappers.UsuarioMapper;
import io.github.Gusta_code22.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuario")
public class UsuarioController {


    private final UsuarioService service;

    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar Usuário")
    public void salvar(@RequestBody @Valid UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);

        service.salvar(usuario);
    }
}
