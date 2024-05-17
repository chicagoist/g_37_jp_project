package de.aittr.g_37_jp_shop.domain.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductSupplyDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private String image;
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ProductSupplyDto that = (ProductSupplyDto) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, image, quantity);
    }

    @Override
    public String toString() {
        return String.format(
                "Product: ID - %d, " +
                        " title - %s, " +
                        " price - %.2f, " +
                        " quantity - %d",
                id, title, price, quantity);
    }
}