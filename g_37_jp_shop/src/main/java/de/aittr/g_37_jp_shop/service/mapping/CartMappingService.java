package de.aittr.g_37_jp_shop.service.mapping;

import de.aittr.g_37_jp_shop.domain.dto.CartDto;
import de.aittr.g_37_jp_shop.domain.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMappingService.class)
public interface CartMappingService {

    CartDto mapEntityToDto(Cart entity);

    @Mapping(target = "id", ignore = true)
    Cart mapDtoToEntity(CartDto dto);
}