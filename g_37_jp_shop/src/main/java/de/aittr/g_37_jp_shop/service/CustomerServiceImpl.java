package de.aittr.g_37_jp_shop.service;

import de.aittr.g_37_jp_shop.domain.dto.CustomerDto;
import de.aittr.g_37_jp_shop.domain.entity.Cart;
import de.aittr.g_37_jp_shop.domain.entity.Customer;
import de.aittr.g_37_jp_shop.domain.entity.Product;
import de.aittr.g_37_jp_shop.exception_handling.exceptions.CustomerNotFoundException;
import de.aittr.g_37_jp_shop.repository.CustomerRepository;
import de.aittr.g_37_jp_shop.service.interfaces.CustomerService;
import de.aittr.g_37_jp_shop.service.interfaces.ProductService;
import de.aittr.g_37_jp_shop.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;
    private CustomerMappingService mappingService;
    private ProductService productService;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mappingService, ProductService productService) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.productService = productService;
    }

    @Override
    public CustomerDto save(CustomerDto dto) {
        Customer entity = mappingService.mapDtoToEntity(dto);
        Cart cart = new Cart();
        entity.setCart(cart);
        cart.setCustomer(entity);

        repository.save(entity);
        return mappingService.mapEntityToDto(entity);
    }

    @Override
    public List<CustomerDto> getAllActiveCustomers() {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public CustomerDto getActiveCustomerById(Long id) {
        return null;
    }

    @Override
    public CustomerDto update(CustomerDto dto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public void restoreById(Long id) {

    }

    @Override
    public long getActiveCustomersTotalCount() {
        return 0;
    }

    @Override
    public BigDecimal getActiveCustomersCartTotalPriceByCustomerId(Long id) {
        return null;
    }

    @Override
    public BigDecimal getAverageProductPriceByCustomerId(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void addProductToCustomerByIds(Long customerId, Long productId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Product product = productService.getProductEntityById(productId);

        customer.getCart().addProduct(product);
    }

    @Override
    public void deleteProductFromCustomerByIds(Long customerId, Long productId) {

    }

    @Override
    public void clearCartByCustomerId(Long id) {

    }
}
