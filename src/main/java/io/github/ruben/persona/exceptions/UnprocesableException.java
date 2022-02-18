package io.github.ruben.persona.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocesableException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UnprocesableException(String message) {
        super(message);
    }

}
