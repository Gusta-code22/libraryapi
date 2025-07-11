package io.github.Gusta_code22.libraryapi.controller;


import io.github.Gusta_code22.libraryapi.controller.dto.AutorDTO;
import io.github.Gusta_code22.libraryapi.controller.mappers.AutorMapper;
import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.model.Usuario;
import io.github.Gusta_code22.libraryapi.security.SecurityService;
import io.github.Gusta_code22.libraryapi.service.AutorService;
import io.github.Gusta_code22.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
// http://localhost:8080/autores
@Tag(name = "Autores")
@Slf4j
public class AutorController implements GenericController {


    private final AutorService service;
    private final SecurityService securityService;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validacao."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
    public ResponseEntity<?> salvarAutor(@RequestBody @Valid AutorDTO dto) {
        log.info("Iniciando cadastro de novo autor: {}", dto);

        var autor = mapper.toEntity(dto);
        log.debug("Autor convertido para entidade: {}", autor);

        service.salvar(autor);
        log.info("Autor salvo com sucesso: {}", autor.getId());

        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter Detalhes", description = "retorna detalhes sobre o autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor nao encontrado."),
    })
    public ResponseEntity<?> detalhesAutor(@PathVariable("id") String id) {
        log.info("Buscando detalhes do autor com ID: {}", id);

        UUID idAutor;
        try {
            idAutor = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("ID inválido fornecido: {}", id, e);
            return ResponseEntity.badRequest().body("ID inválido.");
        }

        return service
                .buscarPorId(idAutor)
                .map(autor -> {
                    log.info("Autor encontrado: {}", autor.getNome());
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> {
                    log.warn("Autor não encontrado com ID: {}", idAutor);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor deletado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Autor possui livro cadastrado."),
    })
    public ResponseEntity<?> deletar(@PathVariable("id") String id) {
        log.info("Solicitada exclusão do autor com ID: {}", id);

        UUID idAutor;
        try {
            idAutor = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("ID inválido fornecido para exclusão: {}", id, e);
            return ResponseEntity.badRequest().body("ID inválido.");
        }

        Optional<Autor> optionalAutor = service.buscarPorId(idAutor);
        if (optionalAutor.isEmpty()) {
            log.warn("Tentativa de deletar autor inexistente: {}", idAutor);
            return ResponseEntity.notFound().build();
        }

        log.info("Deletando autor: {}", optionalAutor.get().getNome());
        service.deleteAutor(optionalAutor.get());

        log.info("Autor deletado com sucesso: {}", idAutor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parametros")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucesso."),
    })
    public ResponseEntity<?> pesquisarAutores(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina) {

        log.info("Pesquisando autores - Nome: {}, Nacionalidade: {}, Página: {}, Tamanho: {}",
                nome, nacionalidade, pagina, tamanhoPagina);

        Page<Autor> resultado = service.pesquisaBySpecification(nome, nacionalidade, pagina, tamanhoPagina);

        log.debug("Quantidade de autores encontrados: {}", resultado.getTotalElements());

        Page<AutorDTO> map = resultado.map(mapper::toDTO);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Autalizar", description = "Atualiza um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor nao encontrado."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO autorDTO) {
        log.info("Solicitada atualização do autor com ID: {}", id);

        UUID idAutor;
        try {
            idAutor = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("ID inválido fornecido para atualização: {}", id, e);
            return ResponseEntity.badRequest().body("ID inválido.");
        }

        Optional<Autor> autorOptional = service.buscarPorId(idAutor);

        if (autorOptional.isEmpty()) {
            log.warn("Autor para atualização não encontrado: {}", idAutor);
            return ResponseEntity.notFound().build();
        }

        log.debug("Autor antes da atualização: {}", autorOptional.get());

        Autor autor = mapper.toEntity(autorDTO);

        service.autualizarAutor(idAutor, autor);
        log.info("Autor atualizado com sucesso: {}", idAutor);

        return ResponseEntity.noContent().build();
    }


}