package de.aittr.g_37_jp_shop.exception_handling.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String productTitle) {
        super(String.format("Product with title %s not found", productTitle));
    }
}
