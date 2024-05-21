package de.aittr.g_37_jp_shop.controller;

import de.aittr.g_37_jp_shop.domain.dto.CustomerDto;
import de.aittr.g_37_jp_shop.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerDto save(@RequestBody CustomerDto customer) {
        return service.save(customer);
    }

    @GetMapping
    public List<CustomerDto> getAll() {
        return service.getAllActiveCustomers();
    }

    @PutMapping
    public void addProduct(
            @RequestParam Long customerId,
            @RequestParam Long productId
    ) {
        service.addProductToCustomerByIds(customerId, productId);
    }
}