package de.aittr.g_37_jp_shop.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotNull(message = "Product title cannot be null")
    @NotBlank(message = "Product title cannot be empty")
    // Banana - V
    // banana - X
    // Ba - X
    // BANANA - X
    // Banana3 - X
    // Banana# - X
    // Банан - X
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "Product title should be at least 3 character length, " +
                    "start with capital letter and may contain only latin characters"
    )
    private String title;

    @Column(name = "price")
    @NotNull(message = "Product price cannot be null")
    @DecimalMin(
            value = "5.00",
            message = "Product price should be greater or equal than 5"
    )
    @DecimalMax(
            value = "100000.00",
            inclusive = false,
            message = "Product price should be lesser than 100 000"
    )
    private BigDecimal price;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "image")
    private String image;

    @Column(name = "quantity")
    private int quantity;

    public Product() {
    }

    public Product(Long id, String title, BigDecimal price, boolean isActive) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.isActive = isActive;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @JsonIgnore
    public boolean isActive() {
        return isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return isActive == product.isActive && quantity == product.quantity && Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(price, product.price) && Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, isActive, image, quantity);
    }

    @Override
    public String toString() {
        return String.format(
                "Product: ID - %d, " +
                        " title - %s, " +
                        " price - %.2f, " +
                        " quantity - %d" +
                        " active - %s",
                id, title, price, quantity, isActive ? "yes" : "no");
    }
}