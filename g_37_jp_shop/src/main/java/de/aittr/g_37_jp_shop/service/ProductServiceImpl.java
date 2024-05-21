package de.aittr.g_37_jp_shop.service;

import de.aittr.g_37_jp_shop.domain.dto.ProductDto;
import de.aittr.g_37_jp_shop.domain.dto.ProductSupplyDto;
import de.aittr.g_37_jp_shop.domain.entity.Product;
import de.aittr.g_37_jp_shop.exception_handling.exceptions.*;
import de.aittr.g_37_jp_shop.repository.ProductRepository;
import de.aittr.g_37_jp_shop.service.interfaces.ProductService;
import de.aittr.g_37_jp_shop.service.mapping.ProductMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductRepository repository;
    private ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto dto) {
        // Join point - то место, куда будет внедрён дополнительный код
//        logger.info("Method called with product {}", dto);

        Product entity = mappingService.mapDtoToEntity(dto);

        try {
            repository.save(entity);
        } catch (Exception e) {
            throw new FourthTestException("Saving product error!", e);
        }

        return mappingService.mapEntityToDto(entity);
    }

    @Override
    public List<ProductDto> getAll() {

//        String productTitle = "Test product";
//
//        logger.info("Database request: get all products");
//        logger.warn("Product with title {} not found", productTitle);
//        logger.error("SQL exception! Incorrect query");

        return repository.findAll()
                .stream()
//                .filter(x -> x.isActive())
                .filter(Product::isActive)
//                .map(x -> mappingService.mapEntityToDto(x))
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {
        if (id == null || id < 1) {
            throw new ThirdTestException("Product id is incorrect");
        }

        Product product = repository.findById(id).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        return mappingService.mapEntityToDto(product);
    }

    @Override
    public Product getProductEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public void update(ProductDto product) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByTitle(String title) {

    }

    @Override
    public void restoreById(Long id) {

    }

    @Override
    public int getTotalQuantity() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }

    @Override
    @Transactional
    public void attachImage(String imageUrl, String productTitle) {
        Product product = repository.findByTitle(productTitle);

        if(product == null) {
            throw new ProductNotFoundException(productTitle);
        }

        product.setImage(imageUrl);
    }

    @Override
    public List<ProductSupplyDto> getProductsForSupply() {
        return repository.findAll()
                .stream()
                .filter(Product::isActive)
                .map(mappingService::mapEntityToSupplyDto)
                .toList();
    }

}