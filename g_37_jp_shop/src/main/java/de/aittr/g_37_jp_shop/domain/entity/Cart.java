package de.aittr.g_37_jp_shop.domain.entity;

import de.aittr.g_37_jp_shop.exception_handling.exceptions.ProductInactiveException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public Cart() {
    }

    public void addProduct(Product product) {
        if(!product.isActive()) {
            throw new ProductInactiveException(product.getTitle());
        }

        products.add(product);
    }

    public List<Product> getAllActiveProducts() {
        return products.stream()
                .filter(Product::isActive)
                .toList();
    }

    public void removeProductById(Long id) {
     //   products.removeIf(x -> x.getId().equals(id));

        // Alternative
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()){
            if(iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }

    public void clear() {
        products.clear();
    }

    public BigDecimal getActiveProductsTotalPrice() {
        return products.stream()
                .filter(Product::isActive)
                .map(Product::getPrice)
                .reduce((x,y) -> x.add(y))
                .orElse(new BigDecimal(0));
    }

    public BigDecimal getActiveProductsAveragePrice() {
        if(products.isEmpty()) {
            return new BigDecimal(0);
        }

        Long productsCount = products.stream()
                .filter(Product::isActive)
                .count();

        return getActiveProductsTotalPrice().divide(new BigDecimal(productsCount));
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(customer, cart.customer) && Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, products);
    }

    @Override
    public String toString() {
        return String.format("Cart: ID - %d, products - %d items",
                id, products.size());
    }
}