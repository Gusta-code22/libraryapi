package io.github.Gusta_code22.libraryapi.exceptions;

import lombok.Getter;

public class CampoInvalidoException extends RuntimeException{

    @Getter
    private String campo;

    public CampoInvalidoException(String campo, String message) {
        super(message);
        this.campo = campo;
    }
}
