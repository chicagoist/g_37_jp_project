package de.aittr.g_37_jp_shop.exception_handling.exceptions;

public class FirstTestException extends RuntimeException{

    public FirstTestException() {
    }

    public FirstTestException(String message) {
        super(message);
    }

    public FirstTestException(String message, Throwable cause) {
        super(message, cause);
    }


}
