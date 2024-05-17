package de.aittr.g_37_jp_supplier.service.interfaces;

import de.aittr.g_37_jp_shop.domain.dto.ProductSupplyDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RequestCalculator {

   Map<String,Integer> calculateRequest(List<ProductSupplyDto> products);
}
