package de.aittr.g_37_jp_shop.exception_handling.exceptions;

public class ProductInactiveException extends RuntimeException{
    public ProductInactiveException(String productTitle) {
        super(String.format("Product with title %s is inactive", productTitle));
    }
}
