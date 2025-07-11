package io.github.Gusta_code22.libraryapi.controller.common;


import io.github.Gusta_code22.libraryapi.controller.dto.ErroCampo;
import io.github.Gusta_code22.libraryapi.controller.dto.ErroResposta;
import io.github.Gusta_code22.libraryapi.exceptions.CampoInvalidoException;
import io.github.Gusta_code22.libraryapi.exceptions.FiltroDePesquisaInvalidoException;
import io.github.Gusta_code22.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.Gusta_code22.libraryapi.exceptions.RegistoDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PasswordUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("Erro de validacao: {}", e.getMessage());
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampo = fieldErrors.stream().map(
                fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())
        ).toList();
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validacao", erroCampo);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErroResposta handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        String mensagem = String.format("Método %s não é suportado para essa URL. Métodos permitidos: %s",
        e.getMethod(),
        e.getSupportedHttpMethods().toString());
        return new ErroResposta(HttpStatus.METHOD_NOT_ALLOWED.value(), mensagem, null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleException(Exception e){
        log.error("Erro inesperado", e);
        String mensagem = String.format("Erro Interno: %s. Entre em contato com a administração",
                e.getMessage());
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), mensagem,
                List.of());
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleorgPostgresqlUtilPSQLException(PSQLException e) {
        String mensagemDetalhada = "Erro no banco de dados: " + e.getMessage();
        return new ErroResposta(HttpStatus.CONFLICT.value(), mensagemDetalhada, List.of());
    }

    @ExceptionHandler(RegistoDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistoDuplicadoException(RegistoDuplicadoException e){

        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validacao",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }


    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroResposta.respotaPadrao(e.getMessage());
    }


    @ExceptionHandler(FiltroDePesquisaInvalidoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleFiltroDePesquisaInvalidoException(FiltroDePesquisaInvalidoException e){
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccesDeniedException(AccessDeniedException e){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso negado", List.of());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResposta handleUnauthorized(HttpClientErrorException.Unauthorized e){
        return new ErroResposta(HttpStatus.UNAUTHORIZED.value(), "Acesso negado", List.of());
    }
}
