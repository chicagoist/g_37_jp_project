package de.aittr.g_37_jp_shop.service.interfaces;

import de.aittr.g_37_jp_shop.domain.dto.ProductDto;
import de.aittr.g_37_jp_shop.domain.dto.ProductSupplyDto;
import de.aittr.g_37_jp_shop.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto product);

    List<ProductDto> getAll();

    ProductDto getById(Long id);

    Product getProductEntityById(Long id);

    void update(ProductDto product);

    void deleteById(Long id);

    void deleteByTitle(String title);

    void restoreById(Long id);

    int getTotalQuantity();

    BigDecimal getTotalPrice();

    BigDecimal getAveragePrice();

    void attachImage(String imageUrl, String productTitle);

    List<ProductSupplyDto> getProductsForSupply();
}