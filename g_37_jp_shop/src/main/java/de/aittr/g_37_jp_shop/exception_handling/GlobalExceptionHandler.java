package de.aittr.g_37_jp_shop.exception_handling;

import de.aittr.g_37_jp_shop.exception_handling.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 3 СПОСОБ обработки исключений
// ПЛЮС - мы создаём глобальный обработчик исключений, который может
// ловить исключения, относящиеся ко всем контроллерам сразу.
// Мы отделяем логику обработки исключений от основной логики нашего приложения,
// тем самым мы облегчаем процесс поддержки и доработки приложения.
// МИНУС - этот способ нам не подходит в случае, когда нам требуется разная
// логика обработки одного и того же исключения в разных контроллерах
@ControllerAdvice
public class GlobalExceptionHandler {

    // Lesson 16 HW
    @ExceptionHandler(FirstTestException.class)
    public ResponseEntity<Response> handleException(FirstTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    // Lesson 16 HW
    @ExceptionHandler(SecondTestException.class)
    public ResponseEntity<Response> handleException(SecondTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    // Lesson 16 HW
    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleException(ThirdTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Lesson 16 HW
    @ExceptionHandler(FourthTestException.class)
    public ResponseEntity<Response> handleException(FourthTestException e) {
        Response response = new Response(e.getMessage(),
                e.getCause().getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleException(ProductNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductInactiveException.class)
    public ResponseEntity<Response> handleException(ProductInactiveException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
