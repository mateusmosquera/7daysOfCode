package br.com.alura.dayscode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FilmIdNotFoundException extends RuntimeException {
    public FilmIdNotFoundException( String message){
        super(message);
    }
}
