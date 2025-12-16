package com.javanauta.usuario.infrastructure.exceptions;

public class ConflictException extends RuntimeException {

    // constructor 1
    public ConflictException(String mensagem) {
        super(mensagem);

    }
    // constructor 2
    public ConflictException(String mensagem, Throwable throwable) {
        super(mensagem);
    }
}
