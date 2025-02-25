package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    RestaurantDTO toDTO(Restaurant restaurant);

    Restaurant toDomain(RestaurantDTO dto);

    List<RestaurantDTO> toDTOList(List<Restaurant> restaurants);

    List<Restaurant> toDomainList(List<RestaurantDTO> dtos);
}
