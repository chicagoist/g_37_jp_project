package de.aittr.g_37_jp_shop.controller;

import de.aittr.g_37_jp_shop.domain.entity.User;
import de.aittr.g_37_jp_shop.exception_handling.Response;
import de.aittr.g_37_jp_shop.service.interfaces.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private UserService service;

    public RegistrationController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public Response register(@RequestBody User user) {
        service.register(user);
        return new Response("Registration complete. Please check your email.");
    }
}