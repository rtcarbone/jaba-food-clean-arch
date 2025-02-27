package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDTO toDTO(Restaurant restaurant);

    Restaurant toDomain(RestaurantDTO dto);

    List<RestaurantDTO> toDTOList(List<Restaurant> restaurants);
}
