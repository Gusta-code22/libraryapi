package io.github.Gusta_code22.libraryapi.controller;

import io.github.Gusta_code22.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.Gusta_code22.libraryapi.controller.dto.ErroResposta;
import io.github.Gusta_code22.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.Gusta_code22.libraryapi.controller.mappers.LivroMapper;
import io.github.Gusta_code22.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.Gusta_code22.libraryapi.model.Livro;
import io.github.Gusta_code22.libraryapi.model.generoLivro;
import io.github.Gusta_code22.libraryapi.service.LivroService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
@Tag(name = "Livros")
@Slf4j
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Cadastrar Livros", description = "Cadastra novo livro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou operacao nao permitida")
    })
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        log.info("Solicitado cadastro de livro: {}", dto);

        try {
            Livro livro = mapper.toEntity(dto);
            log.debug("Livro convertido para entidade: {}", livro);

            livroService.salvar(livro);
            log.info("Livro salvo com sucesso. ID: {}", livro.getId());

            URI url = gerarHeaderLocation(livro.getId());
            return ResponseEntity.created(url).build();

        } catch (OperacaoNaoPermitidaException e) {
            log.warn("Operação não permitida ao salvar livro: {}", e.getMessage());
            var dtoErro = ErroResposta.respotaPadrao(e.getMessage());
            return ResponseEntity.badRequest().body(dtoErro);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Buscar livro por ID", description = "Retorna os detalhes de um livro com base no ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhesId(@PathVariable("id") String id) {
        log.info("Buscando livro com ID: {}", id);

        UUID idLivro;
        try {
            idLivro = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("ID inválido fornecido para busca: {}", id);
            return ResponseEntity.badRequest().build();
        }

        return livroService.buscarPorId(idLivro)
                .map(livro -> {
                    log.info("Livro encontrado: {}", livro.getTitulo());
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> {
                    log.warn("Livro não encontrado: {}", idLivro);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar Livros", description = "Permite buscar livros utilizando filtros opcionais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    })
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) generoLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ) {
        log.info("Realizando pesquisa de livros com filtros - ISBN: {}, Título: {}, Autor: {}, Gênero: {}, Ano: {}",
                isbn, titulo, nomeAutor, genero, anoPublicacao);

        Page<Livro> paginaResultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        log.debug("Quantidade de livros encontrados: {}", paginaResultado.getTotalElements());

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Deletar livro", description = "Remove um livro do sistema pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<?> deletarLivro(@PathVariable("id") String id) {
        log.info("Solicitada exclusão de livro com ID: {}", id);

        UUID idLivro;
        try {
            idLivro = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("ID inválido fornecido para exclusão: {}", id);
            return ResponseEntity.badRequest().build();
        }

        return livroService.buscarPorId(idLivro)
                .map(livro -> {
                    log.info("Livro encontrado. Removendo: {}", livro.getTitulo());
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> {
                    log.warn("Livro para exclusão não encontrado: {}", idLivro);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Atualizar livro", description = "Atualiza os dados de um livro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<?> atualizarLivro(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO livroDTO) {
        log.info("Solicitada atualização de livro com ID: {}", id);

        UUID idLivro;
        try {
            idLivro = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("ID inválido fornecido para atualização: {}", id);
            return ResponseEntity.badRequest().build();
        }

        return livroService.buscarPorId(idLivro).map(livro -> {
            log.debug("Dados anteriores do livro: {}", livro);

            Livro livroAux = mapper.toEntity(livroDTO);

            livro.setIsbn(livroAux.getIsbn());
            livro.setData_publicacao(livroAux.getData_publicacao());
            livro.setTitulo(livroAux.getTitulo());
            livro.setGenero(livroAux.getGenero());
            livro.setPreco(livroAux.getPreco());
            livro.setAutor(livroAux.getAutor());

            livroService.atualizarLivro(livro);

            log.info("Livro atualizado com sucesso: {}", livro.getId());
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> {
            log.warn("Livro para atualização não encontrado: {}", idLivro);
            return ResponseEntity.notFound().build();
        });
    }
}
