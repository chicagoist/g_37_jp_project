package de.aittr.g_37_jp_shop.controller;

import de.aittr.g_37_jp_shop.domain.dto.ProductDto;
import de.aittr.g_37_jp_shop.exception_handling.Response;
import de.aittr.g_37_jp_shop.exception_handling.exceptions.FirstTestException;
import de.aittr.g_37_jp_shop.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.g_37_jp_shop.service.interfaces.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // 1 способ: передача ID в виде части строки запроса
    // GET - localhost:8080/products/example/5

//    @GetMapping("/example/{id}")
//    public Product getById(@PathVariable Long id) {
//        return service.getById(id);
//    }

    // GET - localhost:8080/products/all

    // Просматривать все продукты могут все пользователи,
    // даже не зарегистрированные
    @GetMapping("/all")
    public List<ProductDto> getAll() {
        return service.getAll();
    }

    // 2 способ: передача ID в виде параметра запроса
    // GET - localhost:8080/products?id=5

    // Обращаться к одному конкретному продукту
    // может только зарегистрированный пользователь (с любой ролью)
    @GetMapping
    public ProductDto getById(@RequestParam Long id) {
        return service.getById(id);
    }

    // Сохранять новый продукт в базе данных
    // может только администратор (пользователь с ролью ADMIN)
    @PostMapping
    public ProductDto save(@RequestBody ProductDto product) {
        return service.save(product);
    }

    @PutMapping
    public ProductDto update(@RequestBody ProductDto product) {
        return service.update(product);
    }

    @DeleteMapping
    public void delete(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title
    ) {
        if(id != null) {
            service.deleteById(id);
        } else {
            service.deleteByTitle(title);
        }
    }

    @PutMapping("/restore")
    @Transactional
    public void restore(@RequestParam Long id) {
        if(id != null) {
            service.restoreById(id);
        } else {
            throw new ProductNotFoundException(id);
        }
    }





    // 1 СПОСОБ обработки исключений
    // ПЛЮС - позволяет точечно настроить обработку исключения применительно
    // к данному конкретному контроллеру, в случае, если нам требуется разная
    // логика обработки того же самого исключения в разных контроллерах
    // МИНУС - если нам не требуется разной логики обработки ошибки для
    // разных контроллеров, такой обработчик придётся писать в каждом контроллере
    @ExceptionHandler(FirstTestException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public Response handleException(FirstTestException e) {
        System.out.println("Error666! - " + e.getMessage());
        e.printStackTrace();
        return new Response(e.getMessage());
    }
}