package de.aittr.g_37_jp_shop.service.interfaces;

import de.aittr.g_37_jp_shop.domain.dto.CustomerDto;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    CustomerDto save(CustomerDto dto);
    List<CustomerDto> getAllActiveCustomers();
    CustomerDto getActiveCustomerById(Long id);
    CustomerDto update(CustomerDto dto);
    void deleteById(Long id);
    void deleteByName(String name);
    void restoreById(Long id);
    long getActiveCustomersTotalCount();
    BigDecimal getActiveCustomersCartTotalPriceByCustomerId(Long id);
    BigDecimal getAverageProductPriceByCustomerId(Long id);
    void addProductToCustomerByIds(Long customerId, Long productId);
    void deleteProductFromCustomerByIds(Long customerId, Long productId);
    void clearCartByCustomerId(Long id);
}