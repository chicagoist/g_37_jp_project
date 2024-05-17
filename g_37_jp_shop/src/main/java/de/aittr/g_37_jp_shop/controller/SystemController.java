package de.aittr.g_37_jp_shop.controller;


import de.aittr.g_37_jp_shop.domain.dto.ProductSupplyDto;
import de.aittr.g_37_jp_shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {

    private ProductService productService;

    public SystemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductSupplyDto> getProducts(){
        return productService.getProductsForSupply();
    }
}
