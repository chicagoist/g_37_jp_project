package de.aittr.g_37_jp_shop.domain.dto;

import de.aittr.g_37_jp_shop.domain.entity.Customer;
import de.aittr.g_37_jp_shop.domain.entity.Product;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


public class CartDto {


    private Long id;
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return Objects.equals(id, cartDto.id) && Objects.equals(products, cartDto.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products);
    }

    @Override
    public String toString() {
        return String.format("Cart: ID - %d, products - %d items",
                id, products.size());
    }
}