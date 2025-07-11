package io.github.Gusta_code22.libraryapi.controller;

import io.github.Gusta_code22.libraryapi.controller.dto.ClientDTO;
import io.github.Gusta_code22.libraryapi.controller.mappers.ClientMapper;
import io.github.Gusta_code22.libraryapi.model.Client;
import io.github.Gusta_code22.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Client")
@Slf4j
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    @Operation(summary = "Cadastrar Client", description = "Cadastra um novo Client")
    public ResponseEntity<?> criarClient(@RequestBody @Valid ClientDTO dto){

        log.info("Registrando novo Client: {} com scope: {}", dto.clientId(), dto.scope());
        Client client = mapper.toEntity(dto);
        Client clientSalvo = service.salvar(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientSalvo);

    }

    @GetMapping("/{clientid}")
    @Operation(summary = "Buscar Client Por id", description = "Retorna um client por um Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client Encontrado") ,
            @ApiResponse(responseCode = "404", description = "Client nao encontrado")
    })
    public ResponseEntity<Client> buscarPorClientId(@PathVariable String clientId){
        Client client = service.obterPorClientid(clientId);

        if (client == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

}
