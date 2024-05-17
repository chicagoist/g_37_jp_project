package de.aittr.g_37_jp_shop.exception_handling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 2 спосоп обработки исключений
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SecondTestException extends RuntimeException{

    public SecondTestException() {
    }

    public SecondTestException(String message) {
        super(message);
    }

    public SecondTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
